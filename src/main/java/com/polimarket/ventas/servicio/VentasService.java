package com.polimarket.ventas.servicio;

import com.polimarket.bodega.modelo.Producto;
import com.polimarket.bodega.servicio.BodegaService;
import com.polimarket.rrhh.servicio.UsuarioService;
import com.polimarket.ventas.modelo.*;
import com.polimarket.ventas.repositorio.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class VentasService {

    private final ClienteRepository clienteRepo;
    private final VentaRepository ventaRepo;
    private final BodegaService bodegaService;
    private final UsuarioService usuarioService;

    public VentasService(ClienteRepository clienteRepo, VentaRepository ventaRepo,
                         BodegaService bodegaService, UsuarioService usuarioService) {
        this.clienteRepo = clienteRepo;
        this.ventaRepo = ventaRepo;
        this.bodegaService = bodegaService;
        this.usuarioService = usuarioService;
    }

    @PostConstruct
    public void cargarClientesIniciales() {
        if (clienteRepo.count() == 0) {
            clienteRepo.save(new Cliente("10101010", "Laura Gómez", "Calle 80 # 45-12, Bogotá", "3001112233"));
            clienteRepo.save(new Cliente("20202020", "Empresa Tech SAS", "Av. El Dorado # 68-90, Bogotá", "3104445566"));
        }
    }

    public Map<String, Object> registrarCliente(String username, Cliente cliente) {
        if (!usuarioService.validarAcceso(username, "MOD_VENTAS")) {
            return Map.of("exito", false, "mensaje", "Acceso denegado: sin permiso en MOD_VENTAS");
        }
        return Map.of("exito", true, "cliente", clienteRepo.save(cliente));
    }
    
    public Map<String, Object> actualizarCliente(String username, Cliente cliente) {
        if (!usuarioService.validarAcceso(username, "MOD_VENTAS")) {
            return Map.of("exito", false, "mensaje", "Acceso denegado: sin permiso en MOD_VENTAS");
        }
        return Map.of("exito", true, "cliente", clienteRepo.save(cliente));
    }

    public Map<String, Object> eliminarCliente(String username, String documento) {
        if (!usuarioService.validarAcceso(username, "MOD_VENTAS")) {
            return Map.of("exito", false, "mensaje", "Acceso denegado: sin permiso en MOD_VENTAS");
        }
        clienteRepo.deleteById(documento);
        return Map.of("exito", true, "mensaje", "Cliente eliminado correctamente");
    }


    // Registrar venta: descuenta stock disponible en tiempo real y deja en PENDIENTE_PROGRAMACION
    @Transactional
    public Map<String, Object> registrarVenta(String username, Venta venta) {
        if (!usuarioService.validarAcceso(username, "MOD_VENTAS")) {
            return Map.of("exito", false, "mensaje", "Acceso denegado: sin permiso en MOD_VENTAS");
        }
        if (!clienteRepo.existsById(venta.getDocumentoCliente())) {
            return Map.of("exito", false, "mensaje", "El cliente no está registrado");
        }

        Optional<Producto> prodOpt = bodegaService.buscarProducto(venta.getCodigoProducto());
        if (prodOpt.isEmpty()) {
            return Map.of("exito", false, "mensaje", "Producto no encontrado");
        }

        Producto producto = prodOpt.get();
        if (producto.getStockDisponible() < venta.getCantidad()) {
            return Map.of("exito", false, "mensaje", "Stock insuficiente. Disponible: " + producto.getStockDisponible());
        }

        // Reserva/descuenta el stock disponible para otros vendedores
        producto.setStockDisponible(producto.getStockDisponible() - venta.getCantidad());
        bodegaService.actualizarProducto(producto);

        venta.setVendedorUsername(username);
        venta.setTotalVenta(producto.getPrecioVenta() * venta.getCantidad());
        venta.setEstado("PENDIENTE_PROGRAMACION");
        venta.setFechaVenta(LocalDateTime.now());
        Venta ventaGuardada = ventaRepo.save(venta);

        boolean requiereReposicion = producto.getStockDisponible() <= producto.getStockMinimo();

        return Map.of(
            "exito", true,
            "mensaje", "Venta registrada y enviada a cola de programación de entregas",
            "venta", ventaGuardada,
            "alertaReposicion", requiereReposicion
        );
    }

    public List<Cliente> listarClientes() { return clienteRepo.findAll(); }
    public List<Venta> listarVentas() { return ventaRepo.findAll(); }
    public List<Venta> listarVentasPorEstado(String estado) { return ventaRepo.findByEstado(estado); }
    public Optional<Venta> buscarVenta(Long id) { return ventaRepo.findById(id); }
    public Venta actualizarVenta(Venta venta) { return ventaRepo.save(venta); }
}
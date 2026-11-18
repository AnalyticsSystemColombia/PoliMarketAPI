package com.polimarket.servicio;

import com.polimarket.modelo.Entrega;
import com.polimarket.modelo.Producto;
import com.polimarket.modelo.Venta;
import com.polimarket.repositorio.EntregaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class EntregasService {

    private final EntregaRepository entregaRepo;
    private final VentasService ventasService;
    private final BodegaService bodegaService;
    private final UsuarioService usuarioService;

    public EntregasService(EntregaRepository entregaRepo, VentasService ventasService,
                           BodegaService bodegaService, UsuarioService usuarioService) {
        this.entregaRepo = entregaRepo;
        this.ventasService = ventasService;
        this.bodegaService = bodegaService;
        this.usuarioService = usuarioService;
    }

    // Paso A: Programar el envío de una venta pendiente
    @Transactional
    public Map<String, Object> programarEnvio(String username, Entrega entrega) {
        if (!usuarioService.validarAcceso(username, "MOD_ENTREGAS")) {
            return Map.of("exito", false, "mensaje", "Acceso denegado: sin permiso en MOD_ENTREGAS");
        }
        Optional<Venta> ventaOpt = ventasService.buscarVenta(entrega.getIdVenta());
        if (ventaOpt.isEmpty()) {
            return Map.of("exito", false, "mensaje", "La venta indicada no existe");
        }
        Venta venta = ventaOpt.get();
        if (!"PENDIENTE_PROGRAMACION".equals(venta.getEstado())) {
            return Map.of("exito", false, "mensaje", "Esta venta ya fue programada o despachada");
        }

        venta.setEstado("ENVIO_PROGRAMADO");
        ventasService.actualizarVenta(venta);

        entrega.setEstadoEntrega("PROGRAMADA");
        Entrega guardada = entregaRepo.save(entrega);

        return Map.of("exito", true, "mensaje", "Envío programado con éxito", "entrega", guardada);
    }

    // Paso B: Confirmar salida física de bodega (descuenta stockFisico definitivo)
    @Transactional
    public Map<String, Object> confirmarSalidaFisica(String username, Long idEntrega) {
        if (!usuarioService.validarAcceso(username, "MOD_ENTREGAS")) {
            return Map.of("exito", false, "mensaje", "Acceso denegado: sin permiso en MOD_ENTREGAS");
        }
        Optional<Entrega> entregaOpt = entregaRepo.findById(idEntrega);
        if (entregaOpt.isEmpty()) {
            return Map.of("exito", false, "mensaje", "Entrega no encontrada");
        }

        Entrega entrega = entregaOpt.get();
        if ("DESPACHADA".equals(entrega.getEstadoEntrega())) {
            return Map.of("exito", false, "mensaje", "Esta entrega ya fue despachada anteriormente");
        }

        Venta venta = ventasService.buscarVenta(entrega.getIdVenta()).orElseThrow();
        Producto producto = bodegaService.buscarProducto(venta.getCodigoProducto()).orElseThrow();

        // Descuenta el inventario físico real de bodega
        producto.setStockFisico(producto.getStockFisico() - venta.getCantidad());
        bodegaService.actualizarProducto(producto);

        venta.setEstado("DESPACHADO");
        ventasService.actualizarVenta(venta);

        entrega.setEstadoEntrega("DESPACHADA");
        entrega.setFechaSalidaFisica(LocalDateTime.now());
        entregaRepo.save(entrega);

        return Map.of(
            "exito", true,
            "mensaje", "Salida física registrada y stock de bodega actualizado",
            "nuevoStockFisico", producto.getStockFisico()
        );
    }

    public List<Venta> listarVentasPendientesProgramacion() {
        return ventasService.listarVentasPorEstado("PENDIENTE_PROGRAMACION");
    }

    public List<Entrega> listarEntregas() { return entregaRepo.findAll(); }
}
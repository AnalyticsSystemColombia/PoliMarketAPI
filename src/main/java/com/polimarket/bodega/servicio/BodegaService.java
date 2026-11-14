package com.polimarket.bodega.servicio;

import com.polimarket.bodega.modelo.*;
import com.polimarket.bodega.repositorio.*;
import com.polimarket.proveedores.modelo.Proveedor;
import com.polimarket.proveedores.servicio.ProveedorService;
import com.polimarket.rrhh.servicio.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BodegaService {

    private final ProductoRepository productoRepo;
    private final FacturaCompraRepository facturaRepo;
    private final ProveedorService proveedorService;
    private final UsuarioService usuarioService;

    public BodegaService(ProductoRepository productoRepo, FacturaCompraRepository facturaRepo,
                         ProveedorService proveedorService, UsuarioService usuarioService) {
        this.productoRepo = productoRepo;
        this.facturaRepo = facturaRepo;
        this.proveedorService = proveedorService;
        this.usuarioService = usuarioService;
    }

    public Map<String, Object> crearProducto(String username, Producto producto) {
        if (!usuarioService.validarAcceso(username, "MOD_BODEGA")) {
            return Map.of("exito", false, "mensaje", "Acceso denegado: sin permiso en MOD_BODEGA");
        }
        producto.setStockDisponible(0);
        producto.setStockFisico(0);
        return Map.of("exito", true, "producto", productoRepo.save(producto));
    }

    @Transactional
    public Map<String, Object> registrarFacturaCompra(String username, FacturaCompra factura) {
        if (!usuarioService.validarAcceso(username, "MOD_BODEGA")) {
            return Map.of("exito", false, "mensaje", "Acceso denegado: sin permiso en MOD_BODEGA");
        }
        Optional<Producto> prodOpt = productoRepo.findById(factura.getCodigoProducto());
        if (prodOpt.isEmpty()) {
            return Map.of("exito", false, "mensaje", "El producto no existe en el catálogo");
        }
        if (!proveedorService.existeProveedor(factura.getNitProveedor())) {
            return Map.of("exito", false, "mensaje", "El proveedor no está registrado");
        }

        factura.setFechaRegistro(LocalDateTime.now());
        FacturaCompra facturaGuardada = facturaRepo.save(factura);

        Producto producto = prodOpt.get();
        producto.setStockDisponible(producto.getStockDisponible() + factura.getCantidadIngresada());
        producto.setStockFisico(producto.getStockFisico() + factura.getCantidadIngresada());
        productoRepo.save(producto);

        return Map.of(
            "exito", true,
            "mensaje", "Factura procesada y stock dado de alta en tiempo real",
            "factura", facturaGuardada,
            "nuevoStockDisponible", producto.getStockDisponible()
        );
    }

    public List<Map<String, Object>> consultarAlertasReposicion() {
        List<Map<String, Object>> alertas = new ArrayList<>();
        for (Producto p : productoRepo.findAll()) {
            if (p.getStockDisponible() <= p.getStockMinimo()) {
                String nombreProv = proveedorService.buscarProveedor(p.getNitProveedorPrincipal())
                        .map(Proveedor::getNombre).orElse("Proveedor no asignado");
                alertas.add(Map.of(
                    "codigoProducto", p.getCodigo(),
                    "producto", p.getNombre(),
                    "stockActual", p.getStockDisponible(),
                    "stockMinimo", p.getStockMinimo(),
                    "nitProveedor", p.getNitProveedorPrincipal(),
                    "nombreProveedor", nombreProv
                ));
            }
        }
        return alertas;
    }

    public List<Producto> obtenerProductosDisponiblesParaVenta() { return productoRepo.findByStockDisponibleGreaterThan(0); }
    public Optional<Producto> buscarProducto(String codigo) { return productoRepo.findById(codigo); }
    public Producto actualizarProducto(Producto producto) { return productoRepo.save(producto); }
    public List<Producto> listarTodosProductos() { return productoRepo.findAll(); }
    public List<FacturaCompra> listarFacturas() { return facturaRepo.findAll(); }
}
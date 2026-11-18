package com.polimarket.servicio;

import com.polimarket.modelo.*;
import com.polimarket.repositorio.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class BodegaService {

    private final ProductoRepository productoRepo;
    private final FacturaCompraRepository facturaRepo;
    private final CategoriaRepository categoriaRepo;
    private final UsuarioService usuarioService;

    public BodegaService(ProductoRepository productoRepo, FacturaCompraRepository facturaRepo,
                         CategoriaRepository categoriaRepo, UsuarioService usuarioService) {
        this.productoRepo = productoRepo;
        this.facturaRepo = facturaRepo;
        this.categoriaRepo = categoriaRepo;
        this.usuarioService = usuarioService;
    }

    // --- CRUD CATEGORÍAS (CÓDIGO AUTOMÁTICO CAT-XXX) ---
    public List<Categoria> listarCategorias() {
        return categoriaRepo.findAll();
    }

    public Map<String, Object> guardarCategoria(String username, Categoria cat) {
        if (!usuarioService.validarAcceso(username, "MOD_BODEGA")) {
            return Map.of("exito", false, "mensaje", "Sin permiso en MOD_BODEGA");
        }
        if (cat.getCodigo() == null || cat.getCodigo().isBlank() || "AUTO".equalsIgnoreCase(cat.getCodigo())) {
            long siguiente = categoriaRepo.count() + 1;
            cat.setCodigo(String.format("CAT-%03d", siguiente));
        }
        return Map.of("exito", true, "categoria", categoriaRepo.save(cat));
    }

    public Map<String, Object> eliminarCategoria(String username, String codigo) {
        if (!usuarioService.validarAcceso(username, "MOD_BODEGA")) {
            return Map.of("exito", false, "mensaje", "Sin permiso en MOD_BODEGA");
        }
        categoriaRepo.deleteById(codigo);
        return Map.of("exito", true, "mensaje", "Categoría eliminada");
    }

    // --- CRUD PRODUCTOS (SKU AUTOMÁTICO SKU-XXXX) ---
    public List<Producto> listarTodos() {
        return productoRepo.findAll();
    }

    public List<Producto> obtenerProductosDisponiblesParaVenta() {
        return listarDisponiblesParaVenta();
    }

    public Map<String, Object> guardarProducto(String username, Producto p) {
        if (!usuarioService.validarAcceso(username, "MOD_BODEGA")) {
            return Map.of("exito", false, "mensaje", "Sin permiso en MOD_BODEGA");
        }
        if (p.getCodigo() == null || p.getCodigo().isBlank() || "AUTO".equalsIgnoreCase(p.getCodigo())) {
            long siguiente = productoRepo.count() + 1;
            p.setCodigo(String.format("SKU-%04d", siguiente));
            p.setStockDisponible(0);
            p.setStockFisico(0);
        } else {
            productoRepo.findById(p.getCodigo()).ifPresent(existente -> {
                p.setStockDisponible(existente.getStockDisponible());
                p.setStockFisico(existente.getStockFisico());
            });
        }
        return Map.of("exito", true, "producto", productoRepo.save(p));
    }

    public Map<String, Object> eliminarProducto(String username, String codigo) {
        if (!usuarioService.validarAcceso(username, "MOD_BODEGA")) {
            return Map.of("exito", false, "mensaje", "Sin permiso en MOD_BODEGA");
        }
        productoRepo.deleteById(codigo);
        return Map.of("exito", true, "mensaje", "Producto eliminado");
    }

    // --- CRUD FACTURAS DE COMPRA (CONSECUTIVO AUTOMÁTICO FC-2026-XXXX) ---
    public List<FacturaCompra> listarFacturas() {
        return facturaRepo.findAll();
    }

    @Transactional
    public Map<String, Object> registrarFacturaCompra(String username, FacturaCompra factura) {
        if (!usuarioService.validarAcceso(username, "MOD_BODEGA")) {
            return Map.of("exito", false, "mensaje", "Sin permiso en MOD_BODEGA");
        }
        Optional<Producto> prodOpt = productoRepo.findById(factura.getCodigoProducto());
        if (prodOpt.isEmpty()) {
            return Map.of("exito", false, "mensaje", "El SKU del producto no existe");
        }

        long siguienteFact = facturaRepo.count() + 1;
        factura.setNumeroFactura(String.format("FC-2026-%04d", siguienteFact));
        facturaRepo.save(factura);

        Producto prod = prodOpt.get();
        prod.setStockDisponible(prod.getStockDisponible() + factura.getCantidadIngresada());
        prod.setStockFisico(prod.getStockFisico() + factura.getCantidadIngresada());
        productoRepo.save(prod);

        return Map.of("exito", true, "factura", factura, "producto", prod);
    }

    @Transactional
    public Map<String, Object> eliminarFactura(String username, Long idFactura) {
        if (!usuarioService.validarAcceso(username, "MOD_BODEGA")) {
            return Map.of("exito", false, "mensaje", "Sin permiso en MOD_BODEGA");
        }
        facturaRepo.findById(idFactura).ifPresent(f -> {
            productoRepo.findById(f.getCodigoProducto()).ifPresent(prod -> {
                prod.setStockDisponible(Math.max(0, prod.getStockDisponible() - f.getCantidadIngresada()));
                prod.setStockFisico(Math.max(0, prod.getStockFisico() - f.getCantidadIngresada()));
                productoRepo.save(prod);
            });
            facturaRepo.delete(f);
        });
        return Map.of("exito", true, "mensaje", "Factura anulada y stock ajustado");
    }

    // --- ALERTAS DE REPOSICIÓN ---
    public List<Map<String, Object>> consultarAlertasReposicion() {
        List<Map<String, Object>> alertas = new ArrayList<>();
        for (Producto p : productoRepo.findAll()) {
            if (p.getStockDisponible() <= p.getStockMinimo()) {
                alertas.add(Map.of(
                    "codigo", p.getCodigo(),
                    "producto", (p.getMarca() != null ? p.getMarca() + " " : "") + p.getNombre(),
                    "stockActual", p.getStockDisponible(),
                    "stockMinimo", p.getStockMinimo(),
                    "nitProveedor", p.getNitProveedorPrincipal() != null ? p.getNitProveedorPrincipal() : ""
                ));
            }
        }
        return alertas;
    }
 // --- MÉTODOS PUENTE PARA COMPATIBILIDAD CON VENTAS Y ENTREGAS ---
    public List<Producto> listarDisponiblesParaVenta() {
        return productoRepo.findByStockDisponibleGreaterThan(0);
    }

    public Optional<Producto> buscarProducto(String codigo) {
        return productoRepo.findById(codigo);
    }

    public Producto actualizarProducto(Producto producto) {
        return productoRepo.save(producto);
    }
}

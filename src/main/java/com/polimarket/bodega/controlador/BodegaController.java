package com.polimarket.bodega.controlador;

import com.polimarket.bodega.modelo.*;
import com.polimarket.bodega.servicio.BodegaService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/bodega")
@CrossOrigin(origins = "*")
public class BodegaController {

    private final BodegaService bodegaService;

    public BodegaController(BodegaService bodegaService) {
        this.bodegaService = bodegaService;
    }

    @PostMapping("/productos")
    public Map<String, Object> crearProducto(@RequestHeader("usuario") String username, @RequestBody Producto producto) {
        return bodegaService.crearProducto(username, producto);
    }

    @PostMapping("/facturas")
    public Map<String, Object> ingresarFactura(@RequestHeader("usuario") String username, @RequestBody FacturaCompra factura) {
        return bodegaService.registrarFacturaCompra(username, factura);
    }

    @GetMapping("/productos")
    public List<Producto> obtenerTodosProductos() { return bodegaService.listarTodosProductos(); }

    @GetMapping("/productos/disponibles")
    public List<Producto> obtenerDisponibles() { return bodegaService.obtenerProductosDisponiblesParaVenta(); }

    @GetMapping("/facturas")
    public List<FacturaCompra> obtenerFacturas() { return bodegaService.listarFacturas(); }

    @GetMapping("/alertas-reposicion")
    public List<Map<String, Object>> obtenerAlertas() { return bodegaService.consultarAlertasReposicion(); }
}
package com.polimarket.controller;

import com.polimarket.modelo.*;
import com.polimarket.servicio.BodegaService;
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

    // --- ENDPOINTS CATEGORÍAS ---
    @GetMapping("/categorias")
    public List<Categoria> listarCategorias() { return bodegaService.listarCategorias(); }

    @PostMapping("/categorias")
    public Map<String, Object> guardarCategoria(@RequestHeader("usuario") String username, @RequestBody Categoria cat) {
        return bodegaService.guardarCategoria(username, cat);
    }

    @DeleteMapping("/categorias/{codigo}")
    public Map<String, Object> eliminarCategoria(@RequestHeader("usuario") String username, @PathVariable String codigo) {
        return bodegaService.eliminarCategoria(username, codigo);
    }

    // --- ENDPOINTS PRODUCTOS ---
    @GetMapping("/productos")
    public List<Producto> listarProductos() { return bodegaService.listarTodos(); }

    @PostMapping("/productos")
    public Map<String, Object> guardarProducto(@RequestHeader("usuario") String username, @RequestBody Producto p) {
        return bodegaService.guardarProducto(username, p);
    }

    @DeleteMapping("/productos/{codigo}")
    public Map<String, Object> eliminarProducto(@RequestHeader("usuario") String username, @PathVariable String codigo) {
        return bodegaService.eliminarProducto(username, codigo);
    }

    // --- ENDPOINTS FACTURAS ---
    @GetMapping("/facturas")
    public List<FacturaCompra> listarFacturas() { return bodegaService.listarFacturas(); }

    @PostMapping("/facturas")
    public Map<String, Object> registrarFactura(@RequestHeader("usuario") String username, @RequestBody FacturaCompra f) {
        return bodegaService.registrarFacturaCompra(username, f);
    }

    @DeleteMapping("/facturas/{id}")
    public Map<String, Object> eliminarFactura(@RequestHeader("usuario") String username, @PathVariable Long id) {
        return bodegaService.eliminarFactura(username, id);
    }

    @GetMapping("/alertas-reposicion")
    public List<Map<String, Object>> alertas() { return bodegaService.consultarAlertasReposicion(); }
}

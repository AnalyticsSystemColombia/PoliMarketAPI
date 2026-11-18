package com.polimarket.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polimarket.modelo.Cliente;
import com.polimarket.modelo.Producto;
import com.polimarket.modelo.Venta;
import com.polimarket.servicio.BodegaService;
import com.polimarket.servicio.VentasService;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentasController {

    private final VentasService ventasService;
    private final BodegaService bodegaService;

    public VentasController(VentasService ventasService, BodegaService bodegaService) {
        this.ventasService = ventasService;
        this.bodegaService = bodegaService;
    }
    @PutMapping("/clientes")
    public Map<String, Object> editarCliente(@RequestHeader("usuario") String username, @RequestBody Cliente cliente) {
        return ventasService.actualizarCliente(username, cliente);
    }

    @DeleteMapping("/clientes/{documento}")
    public Map<String, Object> borrarCliente(@RequestHeader("usuario") String username, @PathVariable String documento) {
        return ventasService.eliminarCliente(username, documento);
    }


    @PostMapping("/clientes")
    public Map<String, Object> crearCliente(@RequestHeader("usuario") String username, @RequestBody Cliente cliente) {
        return ventasService.registrarCliente(username, cliente);
    }

    @PostMapping("/registrar")
    public Map<String, Object> registrarVenta(@RequestHeader("usuario") String username, @RequestBody Venta venta) {
        return ventasService.registrarVenta(username, venta);
    }

    @GetMapping("/clientes")
    public List<Cliente> obtenerClientes() { return ventasService.listarClientes(); }

    @GetMapping("/catalogo-disponible")
    public List<Producto> consultarStockTiempoReal() { return bodegaService.obtenerProductosDisponiblesParaVenta(); }

    @GetMapping("/todas")
    public List<Venta> obtenerVentas() { return ventasService.listarVentas(); }
}
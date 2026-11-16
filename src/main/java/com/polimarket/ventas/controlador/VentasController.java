package com.polimarket.ventas.controlador;

import com.polimarket.bodega.modelo.Producto;
import com.polimarket.bodega.servicio.BodegaService;
import com.polimarket.ventas.modelo.*;
import com.polimarket.ventas.servicio.VentasService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

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
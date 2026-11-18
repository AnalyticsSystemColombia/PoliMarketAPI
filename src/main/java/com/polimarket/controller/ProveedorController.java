package com.polimarket.controller;

import com.polimarket.modelo.Proveedor;
import com.polimarket.servicio.ProveedorService;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PostMapping("/crear")
    public Map<String, Object> crearProveedor(@RequestHeader("usuario") String username, @RequestBody Proveedor proveedor) {
        return proveedorService.registrarProveedor(username, proveedor);
    }

    @GetMapping("/todos")
    public List<Proveedor> obtenerProveedores() {
        return proveedorService.listarProveedores();
    }
}
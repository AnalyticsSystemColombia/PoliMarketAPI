package com.polimarket.controller;

import com.polimarket.modelo.Entrega;
import com.polimarket.modelo.Venta;
import com.polimarket.servicio.EntregasService;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/entregas")
@CrossOrigin(origins = "*")
public class EntregasController {

    private final EntregasService entregasService;

    public EntregasController(EntregasService entregasService) {
        this.entregasService = entregasService;
    }

    @PostMapping("/programar")
    public Map<String, Object> programar(@RequestHeader("usuario") String username, @RequestBody Entrega entrega) {
        return entregasService.programarEnvio(username, entrega);
    }

    @PostMapping("/despachar/{idEntrega}")
    public Map<String, Object> despachar(@RequestHeader("usuario") String username, @PathVariable Long idEntrega) {
        return entregasService.confirmarSalidaFisica(username, idEntrega);
    }

    @GetMapping("/pendientes")
    public List<Venta> obtenerPendientes() { return entregasService.listarVentasPendientesProgramacion(); }

    @GetMapping("/todas")
    public List<Entrega> obtenerEntregas() { return entregasService.listarEntregas(); }
}
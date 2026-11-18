package com.polimarket.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polimarket.modelo.Modulo;
import com.polimarket.modelo.PermisoAsignado;
import com.polimarket.modelo.Usuario;
import com.polimarket.servicio.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/crear")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @PostMapping("/modulos/crear")
    public Modulo crearModulo(@RequestBody Modulo modulo) {
        return usuarioService.crearModulo(modulo);
    }

    @PostMapping("/permisos/asignar")
    public PermisoAsignado asignarPermiso(@RequestBody PermisoAsignado permiso) {
        return usuarioService.asignarPermiso(permiso);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credenciales) {
        return usuarioService.login(credenciales.get("username"), credenciales.get("password"));
    }

    @GetMapping("/todos")
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/modulos")
    public List<Modulo> obtenerModulos() {
        return usuarioService.listarModulos();
    }

    @GetMapping("/{username}/permisos")
    public List<PermisoAsignado> obtenerPermisosUsuario(@PathVariable String username) {
        return usuarioService.listarPermisosPorUsuario(username);
    }
    @GetMapping("/permisos/todos")
    public List<PermisoAsignado> obtenerTodosLosPermisos() {
        return usuarioService.listarTodosLosPermisos();
    }

    @DeleteMapping("/permisos/revocar/{username}/{codigoModulo}")
    public Map<String, Object> revocarPermiso(@PathVariable String username, @PathVariable String codigoModulo) {
        usuarioService.revocarPermiso(username, codigoModulo);
        return Map.of("exito", true, "mensaje", "Permiso revocado");
    }
}
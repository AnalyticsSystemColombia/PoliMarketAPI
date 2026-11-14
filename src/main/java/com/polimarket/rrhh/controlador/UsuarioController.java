package com.polimarket.rrhh.controlador;

import com.polimarket.rrhh.modelo.*;
import com.polimarket.rrhh.modelo.Modulo;
import com.polimarket.rrhh.modelo.PermisoAsignado;
import com.polimarket.rrhh.modelo.Usuario;
import com.polimarket.rrhh.servicio.UsuarioService;

import org.springframework.web.bind.annotation.*;
import java.util.*;

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
}
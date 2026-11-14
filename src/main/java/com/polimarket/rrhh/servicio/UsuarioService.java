package com.polimarket.rrhh.servicio;

import com.polimarket.rrhh.modelo.*;
import com.polimarket.rrhh.repositorio.*;

import com.polimarket.rrhh.modelo.Modulo;
import com.polimarket.rrhh.modelo.PermisoAsignado;
import com.polimarket.rrhh.modelo.Usuario;
import com.polimarket.rrhh.repositorio.ModuloRepository;
import com.polimarket.rrhh.repositorio.PermisoRepository;
import com.polimarket.rrhh.repositorio.UsuarioRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final ModuloRepository moduloRepo;
    private final PermisoRepository permisoRepo;

    public UsuarioService(UsuarioRepository usuarioRepo, ModuloRepository moduloRepo, PermisoRepository permisoRepo) {
        this.usuarioRepo = usuarioRepo;
        this.moduloRepo = moduloRepo;
        this.permisoRepo = permisoRepo;
    }

    @PostConstruct
    public void inicializarDatos() {
        if (moduloRepo.count() == 0) {
            moduloRepo.save(new Modulo("MOD_SEGURIDAD", "Seguridad y RH", "Gestión de usuarios, módulos y permisos"));
            moduloRepo.save(new Modulo("MOD_PROVEEDORES", "Proveedores", "Registro y consulta de proveedores"));
            moduloRepo.save(new Modulo("MOD_BODEGA", "Bodega", "Productos, facturas de compra e inventario"));
            moduloRepo.save(new Modulo("MOD_VENTAS", "Ventas", "Consulta de stock en tiempo real y registro de ventas"));
            moduloRepo.save(new Modulo("MOD_ENTREGAS", "Entregas", "Programación de envíos y salida física"));
        }
        if (!usuarioRepo.existsById("admin")) {
            usuarioRepo.save(new Usuario("admin", "1234", "Administrador RH", "Jefe de Recursos Humanos", true));
            permisoRepo.save(new PermisoAsignado("admin", "MOD_SEGURIDAD", "TOTAL"));
        }
    }

    public Usuario crearUsuario(Usuario nuevoUsuario) {
        nuevoUsuario.setActivo(true);
        return usuarioRepo.save(nuevoUsuario);
    }

    public Modulo crearModulo(Modulo nuevoModulo) {
        return moduloRepo.save(nuevoModulo);
    }

    @Transactional
    public PermisoAsignado asignarPermiso(PermisoAsignado nuevoPermiso) {
        permisoRepo.deleteByUsernameAndCodigoModulo(nuevoPermiso.getUsername(), nuevoPermiso.getCodigoModulo());
        return permisoRepo.save(nuevoPermiso);
    }

    public Map<String, Object> login(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(username);
        if (usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(password) && usuarioOpt.get().isActivo()) {
            Usuario u = usuarioOpt.get();
            List<PermisoAsignado> misPermisos = permisoRepo.findByUsername(username);
            return Map.of(
                "autenticado", true,
                "username", u.getUsername(),
                "nombreCompleto", u.getNombreCompleto(),
                "cargo", u.getCargo(),
                "permisos", misPermisos
            );
        }
        return Map.of("autenticado", false, "mensaje", "Credenciales inválidas o usuario inactivo");
    }

    public boolean validarAcceso(String username, String codigoModulo) {
        return permisoRepo.existsByUsernameAndCodigoModulo(username, codigoModulo);
    }

    public List<Usuario> listarUsuarios() { return usuarioRepo.findAll(); }
    public List<Modulo> listarModulos() { return moduloRepo.findAll(); }
    public List<PermisoAsignado> listarPermisosPorUsuario(String username) { return permisoRepo.findByUsername(username); }
}
package com.polimarket.proveedores.servicio;

import com.polimarket.proveedores.modelo.Proveedor;
import com.polimarket.proveedores.repositorio.ProveedorRepository;
import com.polimarket.rrhh.servicio.UsuarioService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepo;
    private final UsuarioService usuarioService;

    public ProveedorService(ProveedorRepository proveedorRepo, UsuarioService usuarioService) {
        this.proveedorRepo = proveedorRepo;
        this.usuarioService = usuarioService;
    }

    public Map<String, Object> registrarProveedor(String username, Proveedor proveedor) {
        if (!usuarioService.validarAcceso(username, "MOD_PROVEEDORES")) {
            return Map.of("exito", false, "mensaje", "Acceso denegado: sin permiso en MOD_PROVEEDORES");
        }
        Proveedor guardado = proveedorRepo.save(proveedor);
        return Map.of("exito", true, "proveedor", guardado);
    }

    public List<Proveedor> listarProveedores() { return proveedorRepo.findAll(); }
    public boolean existeProveedor(String nit) { return proveedorRepo.existsById(nit); }
    public Optional<Proveedor> buscarProveedor(String nit) { return proveedorRepo.findById(nit); }
}
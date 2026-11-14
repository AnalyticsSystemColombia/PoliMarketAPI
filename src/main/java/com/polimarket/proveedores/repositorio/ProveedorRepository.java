package com.polimarket.proveedores.repositorio;

import com.polimarket.proveedores.modelo.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, String> {
}
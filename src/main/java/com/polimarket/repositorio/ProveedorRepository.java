package com.polimarket.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polimarket.modelo.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, String> {
}
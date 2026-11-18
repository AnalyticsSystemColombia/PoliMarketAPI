package com.polimarket.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polimarket.modelo.FacturaCompra;

public interface FacturaCompraRepository extends JpaRepository<FacturaCompra, Long> {
}
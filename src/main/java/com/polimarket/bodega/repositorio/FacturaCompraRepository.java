package com.polimarket.bodega.repositorio;

import com.polimarket.bodega.modelo.FacturaCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaCompraRepository extends JpaRepository<FacturaCompra, Long> {
}
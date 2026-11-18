package com.polimarket.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polimarket.modelo.Venta;

import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByEstado(String estado);
}
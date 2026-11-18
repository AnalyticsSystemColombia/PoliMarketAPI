package com.polimarket.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polimarket.modelo.Entrega;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
}
package com.polimarket.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polimarket.modelo.Modulo;

public interface ModuloRepository extends JpaRepository<Modulo, String> {
}
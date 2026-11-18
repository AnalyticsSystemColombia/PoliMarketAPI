package com.polimarket.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polimarket.modelo.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
}
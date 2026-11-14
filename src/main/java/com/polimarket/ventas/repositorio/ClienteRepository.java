package com.polimarket.ventas.repositorio;

import com.polimarket.ventas.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
}
package com.polimarket.entregas.repositorio;

import com.polimarket.entregas.modelo.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
}
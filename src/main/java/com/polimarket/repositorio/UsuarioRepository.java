package com.polimarket.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polimarket.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
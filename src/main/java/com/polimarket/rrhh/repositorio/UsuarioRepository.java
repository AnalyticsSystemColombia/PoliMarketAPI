package com.polimarket.rrhh.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polimarket.rrhh.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
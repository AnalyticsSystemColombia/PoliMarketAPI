package com.polimarket.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polimarket.modelo.PermisoAsignado;

import java.util.List;

public interface PermisoRepository extends JpaRepository<PermisoAsignado, Long> {
    List<PermisoAsignado> findByUsername(String username);
    boolean existsByUsernameAndCodigoModulo(String username, String codigoModulo);
    void deleteByUsernameAndCodigoModulo(String username, String codigoModulo);
}
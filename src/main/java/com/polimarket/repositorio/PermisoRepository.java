package com.polimarket.repositorio;

import com.polimarket.modelo.PermisoAsignado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PermisoRepository extends JpaRepository<PermisoAsignado, Long> {
    List<PermisoAsignado> findByUsername(String username);
    boolean existsByUsernameAndCodigoModulo(String username, String codigoModulo);
    void deleteByUsernameAndCodigoModulo(String username, String codigoModulo);
}
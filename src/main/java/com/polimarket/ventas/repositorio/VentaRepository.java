package com.polimarket.ventas.repositorio;

import com.polimarket.ventas.modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByEstado(String estado);
}
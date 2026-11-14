package com.polimarket.bodega.repositorio;

import com.polimarket.bodega.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, String> {
    List<Producto> findByStockDisponibleGreaterThan(int cantidad);
}
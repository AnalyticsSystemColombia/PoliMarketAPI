package com.polimarket.bodega.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    private String codigo;
    private String nombre;
    private double precioVenta;
    private int stockDisponible;
    private int stockFisico;
    private int stockMinimo;
    private String nitProveedorPrincipal;

    public Producto() {}

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    public int getStockDisponible() { return stockDisponible; }
    public void setStockDisponible(int stockDisponible) { this.stockDisponible = stockDisponible; }
    public int getStockFisico() { return stockFisico; }
    public void setStockFisico(int stockFisico) { this.stockFisico = stockFisico; }
    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }
    public String getNitProveedorPrincipal() { return nitProveedorPrincipal; }
    public void setNitProveedorPrincipal(String nitProveedorPrincipal) { this.nitProveedorPrincipal = nitProveedorPrincipal; }
}
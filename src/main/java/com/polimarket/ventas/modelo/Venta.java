package com.polimarket.ventas.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documentoCliente;
    private String vendedorUsername;
    private String codigoProducto;
    private int cantidad;
    private double totalVenta;
    private String estado; // PENDIENTE_PROGRAMACION -> ENVIO_PROGRAMADO -> DESPACHADO
    private LocalDateTime fechaVenta;

    public Venta() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDocumentoCliente() { return documentoCliente; }
    public void setDocumentoCliente(String documentoCliente) { this.documentoCliente = documentoCliente; }
    public String getVendedorUsername() { return vendedorUsername; }
    public void setVendedorUsername(String vendedorUsername) { this.vendedorUsername = vendedorUsername; }
    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getTotalVenta() { return totalVenta; }
    public void setTotalVenta(double totalVenta) { this.totalVenta = totalVenta; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }
}
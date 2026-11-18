package com.polimarket.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entregas")
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idVenta;
    private String fechaProgramada;
    private String transportador;
    private String guiaDespacho;
    private String estadoEntrega; // PROGRAMADA -> DESPACHADA
    private LocalDateTime fechaSalidaFisica;

    public Entrega() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdVenta() { return idVenta; }
    public void setIdVenta(Long idVenta) { this.idVenta = idVenta; }
    public String getFechaProgramada() { return fechaProgramada; }
    public void setFechaProgramada(String fechaProgramada) { this.fechaProgramada = fechaProgramada; }
    public String getTransportador() { return transportador; }
    public void setTransportador(String transportador) { this.transportador = transportador; }
    public String getGuiaDespacho() { return guiaDespacho; }
    public void setGuiaDespacho(String guiaDespacho) { this.guiaDespacho = guiaDespacho; }
    public String getEstadoEntrega() { return estadoEntrega; }
    public void setEstadoEntrega(String estadoEntrega) { this.estadoEntrega = estadoEntrega; }
    public LocalDateTime getFechaSalidaFisica() { return fechaSalidaFisica; }
    public void setFechaSalidaFisica(LocalDateTime fechaSalidaFisica) { this.fechaSalidaFisica = fechaSalidaFisica; }
}
package com.polimarket.ventas.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    private String documento;
    private String nombre;
    private String direccionEntrega;
    private String telefono;

    public Cliente() {}

    public Cliente(String documento, String nombre, String direccionEntrega, String telefono) {
        this.documento = documento;
        this.nombre = nombre;
        this.direccionEntrega = direccionEntrega;
        this.telefono = telefono;
    }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
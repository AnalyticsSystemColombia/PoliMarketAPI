package com.polimarket.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "proveedores")
public class Proveedor {
    @Id
    private String nit;
    private String nombre;
    private String contacto;
    private String telefono;

    public Proveedor() {}

    public Proveedor(String nit, String nombre, String contacto, String telefono) {
        this.nit = nit;
        this.nombre = nombre;
        this.contacto = contacto;
        this.telefono = telefono;
    }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
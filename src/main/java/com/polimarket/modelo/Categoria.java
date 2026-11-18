package com.polimarket.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    private String codigo;      // Ej: CAT-001, CAT-002
    private String marca;       // Ej: Lenovo, Dell, HP
    private String linea;       // Ej: Empresarial, Gaming, Ultrabook
    private String descripcion;

    public Categoria() {}

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getLinea() { return linea; }
    public void setLinea(String linea) { this.linea = linea; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}

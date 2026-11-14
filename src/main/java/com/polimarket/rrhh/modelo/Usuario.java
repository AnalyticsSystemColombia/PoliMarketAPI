package com.polimarket.rrhh.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    private String username;
    private String password;
    private String nombreCompleto;
    private String cargo;
    private boolean activo;

    public Usuario() {}

    public Usuario(String username, String password, String nombreCompleto, String cargo, boolean activo) {
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.cargo = cargo;
        this.activo = activo;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
package com.polimarket.rrhh.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "permisos_asignados")
public class PermisoAsignado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String codigoModulo;
    private String nivelAcceso;

    public PermisoAsignado() {}

    public PermisoAsignado(String username, String codigoModulo, String nivelAcceso) {
        this.username = username;
        this.codigoModulo = codigoModulo;
        this.nivelAcceso = nivelAcceso;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getCodigoModulo() { return codigoModulo; }
    public void setCodigoModulo(String codigoModulo) { this.codigoModulo = codigoModulo; }
    public String getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(String nivelAcceso) { this.nivelAcceso = nivelAcceso; }
}
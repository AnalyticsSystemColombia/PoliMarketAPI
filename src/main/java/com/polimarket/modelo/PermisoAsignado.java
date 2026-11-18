package com.polimarket.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "permisos_asignados")
public class PermisoAsignado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String codigoModulo;
    
    // Permisos granulares por acción CRUD
    private boolean puedeLeer;
    private boolean puedeCrear;
    private boolean puedeEditar;
    private boolean puedeEliminar;

    public PermisoAsignado() {}

    public PermisoAsignado(String username, String codigoModulo, boolean puedeLeer, boolean puedeCrear, boolean puedeEditar, boolean puedeEliminar) {
        this.username = username;
        this.codigoModulo = codigoModulo;
        this.puedeLeer = puedeLeer;
        this.puedeCrear = puedeCrear;
        this.puedeEditar = puedeEditar;
        this.puedeEliminar = puedeEliminar;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getCodigoModulo() { return codigoModulo; }
    public void setCodigoModulo(String codigoModulo) { this.codigoModulo = codigoModulo; }
    public boolean isPuedeLeer() { return puedeLeer; }
    public void setPuedeLeer(boolean puedeLeer) { this.puedeLeer = puedeLeer; }
    public boolean isPuedeCrear() { return puedeCrear; }
    public void setPuedeCrear(boolean puedeCrear) { this.puedeCrear = puedeCrear; }
    public boolean isPuedeEditar() { return puedeEditar; }
    public void setPuedeEditar(boolean puedeEditar) { this.puedeEditar = puedeEditar; }
    public boolean isPuedeEliminar() { return puedeEliminar; }
    public void setPuedeEliminar(boolean puedeEliminar) { this.puedeEliminar = puedeEliminar; }
}

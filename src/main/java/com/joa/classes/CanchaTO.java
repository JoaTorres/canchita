package com.joa.classes;

import java.util.List;

public class CanchaTO {
    private int id;
    private String nombre;
    private String descripcion;
    
    private List<ReservaTO> reservas;

    public CanchaTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ReservaTO> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaTO> reservas) {
        this.reservas = reservas;
    }
    
    
    
    
}

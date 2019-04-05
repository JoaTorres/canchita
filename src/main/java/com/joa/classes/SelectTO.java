package com.joa.classes;

import java.io.Serializable;

public class SelectTO implements Serializable{

    private int id;
    private String descripcion;
    private int marcado;

    public SelectTO() {
    }

    public SelectTO(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMarcado() {
        return marcado;
    }

    public void setMarcado(int marcado) {
        this.marcado = marcado;
    }

    
    
}

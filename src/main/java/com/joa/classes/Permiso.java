/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.classes;

/**
 *
 * @author developer
 */
public class Permiso {
    private Modulo modulo;
    private boolean listar;
    private boolean insertar;
    private boolean editar;
    private boolean eliminar;
    private boolean reportar;

    public Permiso() {
    }

    public boolean isInsertar() {
        return insertar;
    }

    public void setInsertar(boolean insertar) {
        this.insertar = insertar;
    }
    
    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public boolean isListar() {
        return listar;
    }

    public void setListar(boolean listar) {
        this.listar = listar;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public boolean isEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    public boolean isReportar() {
        return reportar;
    }

    public void setReportar(boolean reportar) {
        this.reportar = reportar;
    }

   
    
    
    
}

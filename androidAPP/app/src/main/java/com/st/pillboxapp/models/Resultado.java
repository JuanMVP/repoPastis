package com.st.pillboxapp.models;

import java.util.List;

public class Resultado {

    private String nregistro;
    private String nombre;
    private String dosis;
    private List<Foto> fotos;
    private String id_persona;

    public Resultado(){}

    public Resultado(String nregistro, String nombre, String dosis, List<Foto> fotos) {
        this.nregistro = nregistro;
        this.nombre = nombre;
        this.dosis = dosis;
        this.fotos = fotos;
    }

    public Resultado(String nombre, String dosis) {
        this.nombre = nombre;
        this.dosis = dosis;

    }

    public Resultado(String nombre, String dosis, String id_persona) {
        this.nombre = nombre;
        this.dosis = dosis;
        this.id_persona = id_persona;
    }

    public String getId_persona() {
        return id_persona;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
    }

    public String getNregistro() {
        return nregistro;
    }

    public void setNregistro(String nregistro) {
        this.nregistro = nregistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    @Override
    public String toString() {
        return "Resultado{" +
                "nombre='" + nombre + '\'' +
                ", dosis='" + dosis + '\'' +
                ", id_persona='" + id_persona + '\'' +
                '}';
    }
}

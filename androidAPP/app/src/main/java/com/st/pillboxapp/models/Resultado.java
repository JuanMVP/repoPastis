package com.st.pillboxapp.models;

import java.util.List;

public class Resultado {

    private String nregistro;
    private String nombre;
    private String dosis;
    private List<Foto> fotos;

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
                "nregistro='" + nregistro + '\'' +
                ", nombre='" + nombre + '\'' +
                ", dosis='" + dosis + '\'' +
                '}';
    }
}

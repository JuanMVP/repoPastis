package com.st.pillboxapp.models;

public class Medicamento {

    private String nregistro;
    private String nombre;
    private String dosis;
    private String imagenUrl;


    public Medicamento(){}

    public Medicamento(String nregistro, String nombre, String dosis, String imagenUrl) {
        this.nregistro = nregistro;
        this.nombre = nombre;
        this.dosis = dosis;
        this.imagenUrl = imagenUrl;
    }

    public Medicamento(String nombre, String dosis) {
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

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}

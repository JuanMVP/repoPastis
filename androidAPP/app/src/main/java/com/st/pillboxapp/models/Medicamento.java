package com.st.pillboxapp.models;

public class Medicamento {

    private String id;
    private String nombre;
    private String dosis;
    private String imagenUrl;


    public Medicamento(){}

    public Medicamento(String id, String nombre, String dosis) {
        this.id = id;
        this.nombre = nombre;
        this.dosis = dosis;
    }

    public Medicamento(String nombre, String dosis, String imagenUrl) {
        this.nombre = nombre;
        this.dosis = dosis;
        this.imagenUrl = imagenUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    @Override
    public String toString() {
        return "Medicamento{" +
                "nombre='" + nombre + '\'' +
                ", dosis='" + dosis + '\'' +
                ", imagenUrl='" + imagenUrl + '\'' +
                '}';
    }
}

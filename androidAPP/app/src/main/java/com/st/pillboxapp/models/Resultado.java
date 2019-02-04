package com.st.pillboxapp.models;

public class Resultado {

    private String nregistro;
    private String nombre;
    private String dosis;

    public Resultado(){}

    public Resultado(String nregistro, String nombre, String dosis) {
        this.nregistro = nregistro;
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

    @Override
    public String toString() {
        return "Resultado{" +
                "nregistro='" + nregistro + '\'' +
                ", nombre='" + nombre + '\'' +
                ", dosis='" + dosis + '\'' +
                '}';
    }
}

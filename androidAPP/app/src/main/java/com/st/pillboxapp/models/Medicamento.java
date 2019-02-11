package com.st.pillboxapp.models;

public class Medicamento {

    private String nregistro;
    private String nombre;
    private Integer cantidad;
    private Float gramos;
    private String imagenUrl;


    public Medicamento(){}

    public Medicamento(String nregistro, String nombre, Integer cantidad, Float gramos, String imagenUrl) {
        this.nregistro = nregistro;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.gramos = gramos;
        this.imagenUrl = imagenUrl;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getGramos() {
        return gramos;
    }

    public void setGramos(Float gramos) {
        this.gramos = gramos;
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
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", gramos=" + gramos +
                ", imagenUrl='" + imagenUrl + '\'' +
                '}';
    }
}

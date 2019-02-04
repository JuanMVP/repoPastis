package com.st.pillboxapp.models;

public class Medicamento {

    private Long id;
    private String nombre;
    private Integer cantidad;
    private Float gramos;
    private String imagenUrl;


    public Medicamento(){}

    public Medicamento(Long id, String nombre, Integer cantidad, Float gramos, String imagenUrl) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.gramos = gramos;
        this.imagenUrl = imagenUrl;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", gramos=" + gramos +
                ", imagenUrl='" + imagenUrl + '\'' +
                '}';
    }
}

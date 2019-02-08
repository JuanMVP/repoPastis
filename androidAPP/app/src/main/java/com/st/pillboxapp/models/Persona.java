package com.st.pillboxapp.models;

import java.util.Objects;

public class Persona {

    private String id;
    private String nombre;
    private String fecha_nacimiento;

    public Persona(){}

    public Persona(String id, String nombre, String fecha_nacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
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

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(id, persona.id) &&
                Objects.equals(nombre, persona.nombre) &&
                Objects.equals(fecha_nacimiento, persona.fecha_nacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, fecha_nacimiento);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                '}';
    }
}

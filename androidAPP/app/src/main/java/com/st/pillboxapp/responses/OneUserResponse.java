package com.st.pillboxapp.responses;

import com.st.pillboxapp.models.Medicamento;
import com.st.pillboxapp.models.Persona;

import java.util.List;

public class OneUserResponse {

    private String id;
    private String nombre;
    private String picture;
    private List<Persona> personas;
    private List<Medicamento> medicamentos;

    public OneUserResponse() {
    }

    public OneUserResponse(String id, String nombre, String picture, List<Persona> personas) {
        this.id = id;
        this.nombre = nombre;
        this.picture = picture;
        this.personas = personas;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    @Override
    public String toString() {
        return "OneUserResponse{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", picture='" + picture + '\'' +
                ", personas=" + personas +
                '}';
    }
}

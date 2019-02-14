package com.st.pillboxapp.models;

import java.util.Objects;

public class Persona {

    private String id;
    private String nombre;
    private String fecha_nacimiento;
    private String user_id;

    public Persona(){}


    public Persona(String nombre, String fecha_nacimiento, String user_id) {
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.user_id = user_id;
    }



    public Persona(String id, String nombre, String fecha_nacimiento, String user_id) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
    public String toString() {
        return nombre;
    }
}

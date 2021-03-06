package com.st.pillboxapp.models;

import java.util.List;

public class PersonaConMed {

    private String id;
    private String nombre;
    private String fecha_nacimiento;
    private String user_id;
    private List<Medicamento> medicamentos;

    public PersonaConMed(){}


    public PersonaConMed(String nombre, String fecha_nacimiento, String user_id) {
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.user_id = user_id;
    }



    public PersonaConMed(String id, String nombre, String fecha_nacimiento, String user_id) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.user_id = user_id;
    }

    public PersonaConMed(String id, String nombre, String fecha_nacimiento, String user_id, List<Medicamento> medicamentos) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.user_id = user_id;
        this.medicamentos = medicamentos;
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

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
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

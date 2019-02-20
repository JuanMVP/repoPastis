package com.st.pillboxapp.responses;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.models.Tomas;

import java.util.List;
import java.util.Objects;

public class PersonaResponse {

    private String id;
    private String nombre;
    private String fechaNacimiento;
    private List<Resultado> medicamentos;
    private List<Tomas> listaTomas;

    public PersonaResponse() { }

    public PersonaResponse(String id, String nombre, String fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    public PersonaResponse(String id, String nombre, String fechaNacimiento, List<Resultado> medicamentos) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.medicamentos = medicamentos;
    }

    public PersonaResponse(String id, String nombre, String fechaNacimiento, List<Resultado> medicamentos, List<Tomas> listaTomas) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.medicamentos = medicamentos;
        this.listaTomas = listaTomas;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public List<Resultado> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Resultado> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Tomas> getListaTomas() {
        return listaTomas;
    }

    public void setListaTomas(List<Tomas> listaTomas) {
        this.listaTomas = listaTomas;
    }

    @Override
    public String toString() {
        return "PersonaResponse{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                '}';
    }
}

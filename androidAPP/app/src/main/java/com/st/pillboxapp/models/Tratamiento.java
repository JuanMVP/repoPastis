package com.st.pillboxapp.models;

import java.util.List;

public class Tratamiento {

    private String nombreTratamiento;
    private String periodoToma;
    private int diasDuracionTratamiento;
    private List<Medicamento> listaMedicamentosTratamientos;



    public Tratamiento (){}

    public Tratamiento(String nombreTratamiento, String periodoToma, int diasDuracionTratamiento, List<Medicamento> listaMedicamentosTratamientos) {
        this.nombreTratamiento = nombreTratamiento;
        this.periodoToma = periodoToma;
        this.diasDuracionTratamiento = diasDuracionTratamiento;
        this.listaMedicamentosTratamientos = listaMedicamentosTratamientos;
    }


    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public String getPeriodoToma() {
        return periodoToma;
    }

    public void setPeriodoToma(String periodoToma) {
        this.periodoToma = periodoToma;
    }

    public int getDiasDuracionTratamiento() {
        return diasDuracionTratamiento;
    }

    public void setDiasDuracionTratamiento(int diasDuracionTratamiento) {
        this.diasDuracionTratamiento = diasDuracionTratamiento;
    }

    public List<Medicamento> getListaMedicamentosTratamientos() {
        return listaMedicamentosTratamientos;
    }

    public void setListaMedicamentosTratamientos(List<Medicamento> listaMedicamentosTratamientos) {
        this.listaMedicamentosTratamientos = listaMedicamentosTratamientos;
    }

    @Override
    public String toString() {
        return "Tratamiento{" +
                "nombreTratamiento='" + nombreTratamiento + '\'' +
                ", periodoToma='" + periodoToma + '\'' +
                ", diasDuracionTratamiento=" + diasDuracionTratamiento +
                ", listaMedicamentosTratamientos=" + listaMedicamentosTratamientos +
                '}';
    }
}

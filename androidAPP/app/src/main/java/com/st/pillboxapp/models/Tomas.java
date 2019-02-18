package com.st.pillboxapp.models;

public class Tomas {

    private Medicamento medicamento;
    private Persona persona;
    private String dia_semana;
    private String hora_toma;

    public Tomas (){}


    public Tomas(Medicamento medicamento, Persona persona, String dia_semana, String hora_toma) {
        this.medicamento = medicamento;
        this.persona = persona;
        this.dia_semana = dia_semana;
        this.hora_toma = hora_toma;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    public String getHora_toma() {
        return hora_toma;
    }

    public void setHora_toma(String hora_toma) {
        this.hora_toma = hora_toma;
    }


    @Override
    public String toString() {
        return "Tomas{" +
                "medicamento=" + medicamento +
                ", persona=" + persona +
                ", dia_semana='" + dia_semana + '\'' +
                ", hora_toma='" + hora_toma + '\'' +
                '}';
    }
}

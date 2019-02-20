package com.st.pillboxapp.models;

public class Tomas {

    private String medicamento_id;
    private String persona_id;
    private String dia_semana;
    private String hora_toma;

    public Tomas (){}


    public Tomas(String medicamento_id, String persona_id, String dia_semana, String hora_toma) {
        this.medicamento_id = medicamento_id;
        this.persona_id = persona_id;
        this.dia_semana = dia_semana;
        this.hora_toma = hora_toma;
    }

    @Override
    public String toString() {
        return "Tomas{" +
                "medicamento_id='" + medicamento_id + '\'' +
                ", persona_id='" + persona_id + '\'' +
                ", dia_semana='" + dia_semana + '\'' +
                ", hora_toma='" + hora_toma + '\'' +
                '}';
    }

    public String getMedicamento_id() {
        return medicamento_id;
    }

    public void setMedicamento_id(String medicamento_id) {
        this.medicamento_id = medicamento_id;
    }

    public String getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(String persona_id) {
        this.persona_id = persona_id;
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
}

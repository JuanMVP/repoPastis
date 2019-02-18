package com.st.pillboxapp.responses;

import com.st.pillboxapp.models.Medicamento;

import java.util.List;

public class MyMedicamentoResponse {

    private List<Medicamento> medicamentos;

    public MyMedicamentoResponse() { }

    public MyMedicamentoResponse(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    @Override
    public String toString() {
        return "MyMedicamentoResponse{" +
                "medicamentos=" + medicamentos +
                '}';
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

}

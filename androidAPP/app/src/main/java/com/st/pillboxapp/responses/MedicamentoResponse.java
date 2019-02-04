package com.st.pillboxapp.responses;

import com.st.pillboxapp.models.Resultado;

import java.util.List;

public class MedicamentoResponse {

    private List<Resultado> resultados;

    public MedicamentoResponse(){}

    public MedicamentoResponse(List<Resultado> resultados) {
        this.resultados = resultados;
    }

    public List<Resultado> getResultados() {
        return resultados;
    }

    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }

    @Override
    public String toString() {
        return "MedicamentoResponse{" +
                "resultados=" + resultados +
                '}';
    }
}

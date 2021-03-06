package com.st.pillboxapp.responses;

import com.st.pillboxapp.models.Foto;
import com.st.pillboxapp.models.Resultado;

import java.util.List;

public class ResultadoResponse {

    private List<Resultado> resultados;
    private List<Foto> fotos;

    public ResultadoResponse() {
    }

    public ResultadoResponse(List<Resultado> resultados, List<Foto> fotos) {
        this.resultados = resultados;
        this.fotos = fotos;
    }

    public List<Resultado> getResultados() {
        return resultados;
    }


    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    @Override
    public String toString() {
        return "ResultadoResponse{" +
                "resultados=" + resultados +
                ", fotos=" + fotos +
                '}';
    }
}

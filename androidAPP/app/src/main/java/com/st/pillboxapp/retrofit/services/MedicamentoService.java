package com.st.pillboxapp.retrofit.services;

import com.st.pillboxapp.models.Medicamento;
import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.responses.MedicamentoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MedicamentoService {

    @GET("/cima/rest/medicamentos")
    Call<MedicamentoResponse> getMedicamentos(@Query("nombre") String nombre);

    @GET("/cima/rest/medicamentos")
    Call<MedicamentoResponse> getOneMedicamento(@Query("nregistro") String nregistro);

    @POST("/medicamentos")
    Call<Medicamento> addMedicamento(@Body Medicamento medicamento);
}

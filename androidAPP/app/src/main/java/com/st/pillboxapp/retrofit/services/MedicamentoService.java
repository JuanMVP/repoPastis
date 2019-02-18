package com.st.pillboxapp.retrofit.services;

import com.st.pillboxapp.models.Medicamento;
import com.st.pillboxapp.responses.MyMedicamentoResponse;
import com.st.pillboxapp.responses.ResultadoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MedicamentoService {

    @POST("/medicamentos")
    Call<MyMedicamentoResponse> register(@Body Medicamento medicamento);

    @GET("/medicamentos")
    Call<MyMedicamentoResponse> getMisMedicamentos();

    @GET("/cima/rest/medicamentos")
    Call<ResultadoResponse> getMedicamentos(@Query("nombre") String nombre);

    @GET("/cima/rest/medicamentos")
    Call<ResultadoResponse> getOneMedicamento(@Query("nregistro") String nregistro);

    @POST("/medicamentos")
    Call<Medicamento> addMedicamento(@Body Medicamento medicamento);
}

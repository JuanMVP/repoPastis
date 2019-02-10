package com.st.pillboxapp.retrofit.services;

import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.responses.PersonaResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PersonaService {

    @POST("/personas")
    Call<PersonaResponse> register(@Body Persona persona);

    @GET("/personas/{id}")
    Call<PersonaResponse> findOne(@Path("id") String id);

    @DELETE("/personas/{id}")
    Call<PersonaResponse> deleteOne(@Path("id") String id);

}

package com.st.pillboxapp.retrofit.services;

import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.responses.PersonaResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PersonaService {

    @POST("/personas")
    Call<PersonaResponse> register(@Body Persona persona);

}

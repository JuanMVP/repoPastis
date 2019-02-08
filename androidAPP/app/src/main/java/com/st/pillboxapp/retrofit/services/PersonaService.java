package com.st.pillboxapp.retrofit.services;

import com.st.pillboxapp.models.Persona;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PersonaService {

    @POST("/personas")
    Call register(@Body Persona persona);

}

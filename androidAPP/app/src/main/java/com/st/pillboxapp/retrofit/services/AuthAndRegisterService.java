package com.st.pillboxapp.retrofit.services;

import com.st.pillboxapp.models.Register;
import com.st.pillboxapp.responses.AuthAndRegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthAndRegisterService {

    @POST("/auth")
    Call<AuthAndRegisterResponse> login(@Header("Authorization") String authorization);

    @POST("/users")
    Call<AuthAndRegisterResponse> register(@Body Register registro);
}

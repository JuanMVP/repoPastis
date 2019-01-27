package com.st.pillboxapp.retrofit.services;

import com.st.pillboxapp.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {

    @POST("/auth")
    Call<LoginResponse> login(@Query("access_token") String access_token, @Header("Authorization") String authorization);
}

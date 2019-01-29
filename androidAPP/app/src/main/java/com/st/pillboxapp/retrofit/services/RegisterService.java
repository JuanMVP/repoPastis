package com.st.pillboxapp.retrofit.services;

import com.st.pillboxapp.models.Register;
import com.st.pillboxapp.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("/users")
    Call<LoginResponse> register(@Body Register registro);

}

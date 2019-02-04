package com.st.pillboxapp.retrofit.services;

import com.st.pillboxapp.responses.OneUserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("/users/{id}")
    Call<OneUserResponse> oneUserById(@Path("id") String id);

}

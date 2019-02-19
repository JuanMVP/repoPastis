package com.st.pillboxapp.retrofit.services;

import com.st.pillboxapp.models.ResponseContainer;
import com.st.pillboxapp.models.Tomas;
import com.st.pillboxapp.responses.TomasResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TomasService {

    @GET("/tomas")
    Call<ResponseContainer<Tomas>> getTomas();


}

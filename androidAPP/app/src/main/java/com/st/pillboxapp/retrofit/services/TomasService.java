package com.st.pillboxapp.retrofit.services;

import com.st.pillboxapp.models.ResponseContainer;
import com.st.pillboxapp.models.Tomas;
import com.st.pillboxapp.responses.TomasResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TomasService {

    @GET("/tomas")
    Call<ResponseContainer<Tomas>> getTomas();

    @POST("/persona")
    Call<Tomas> add (@Body Tomas tomas);


}

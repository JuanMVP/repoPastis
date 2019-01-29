package com.st.pillboxapp.retrofit.generator;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginServiceGenerator {


    //private static final String BASE_URL = "https://authentication-fppitlaelx.now.sh/";
    private static final String BASE_URL = "https://pillboxapi.herokuapp.com/";


    public static final String access_token = "ZPgUSMUlq4N7hIbuyRU1BsUn1U457dz6";

    //public static final String access_token = "lNeTI8waAqmpUZa7QSiLv53rqSnlsldv";

    public static String jwToken = null;

    private static Retrofit.Builder builder =
            new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = null;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    public static <S> S createService(Class<S> serviceClass) {

        if(!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            builder.client(httpClient.build());
            retrofit = builder.build();

        }

        return retrofit.create(serviceClass);
    }







}

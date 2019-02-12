package com.st.pillboxapp.retrofit.generator;

import com.st.pillboxapp.models.TipoAutenticacion;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceApiGenerator {

    private static final String BASE_URL = "https://cima.aemps.es/";

    public static final String MASTER_KEY = "ZPgUSMUlq4N7hIbuyRU1BsUn1U457dz6";

    public static String jwToken = null;

    private static Retrofit.Builder builder =
            new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = null;

    private static TipoAutenticacion tipoActual = null;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();


    // Sin autenticación
    //#######################################################################################//

    public static <S> S createService(Class<S> serviceClass) {

        return createService(serviceClass, null, TipoAutenticacion.SIN_AUTENTICACION);
    }


    // Autenticación básica
    //#######################################################################################//
    public static <S> S createService(Class<S> serviceClass, String username, String password) {

        if (!(username.isEmpty() || password.isEmpty())) {

            String credentials = Credentials.basic(username, password);
            return createService(serviceClass, credentials, TipoAutenticacion.BASIC);

        }

        return createService(serviceClass, null, TipoAutenticacion.SIN_AUTENTICACION);
    }


    public static <S> S createService(Class<S> serviceClass, final String authtoken, final TipoAutenticacion tipo) {

        if (retrofit == null || tipoActual != tipo) {

            httpClientBuilder.interceptors().clear();

            httpClientBuilder.addInterceptor(logging);

            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("User-Agent", "PillboxApi")
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            if (tipo == TipoAutenticacion.SIN_AUTENTICACION || tipo == TipoAutenticacion.BASIC) {

                httpClientBuilder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalUrl = original.url();

                        HttpUrl url = originalUrl.newBuilder()
                                .addQueryParameter("access_token", MASTER_KEY)
                                .build();

                        Request request = original.newBuilder()
                                .url(url)
                                .build();


                        return chain.proceed(request);
                    }
                });
            }

            if (authtoken != null) {

                httpClientBuilder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        String token = null;
                        if (tipo == TipoAutenticacion.JWT && !authtoken.startsWith("Bearer "))
                            token = "Bearer " + authtoken;
                        else
                            token = authtoken;


                        Request request = original.newBuilder()
                                .header("Authorization", token)
                                .build();

                        return chain.proceed(request);
                    }
                });
            }

            tipoActual = tipo;

            builder.client(httpClientBuilder.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }

}

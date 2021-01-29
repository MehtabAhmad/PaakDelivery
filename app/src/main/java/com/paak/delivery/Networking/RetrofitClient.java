package com.paak.delivery.Networking;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static RetrofitClient retrofitClient;
    private static final String BASE_URL = "https://my-json-server.typicode.com/MehtabAhmad/delivery/";

    private RetrofitClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static APIService getService() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofit.create(APIService.class);
    }
}

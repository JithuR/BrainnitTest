package com.brainnit.test.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.brainnit.test.utils.ConstantsKt.BASE_URL_API;


public class APIEngine {

    private static Retrofit retrofit = null;

    /*
    * Creating retrofit with base url
    * */
    public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(50, TimeUnit.SECONDS)
            .connectTimeout(50, TimeUnit.SECONDS)
            .build();

}

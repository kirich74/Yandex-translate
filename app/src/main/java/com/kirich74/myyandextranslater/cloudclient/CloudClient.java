package com.kirich74.myyandextranslater.cloudclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kirill Pilipenko on 13.04.2017.
 */

public class CloudClient {

    static final String BASE_URL = "https://translate.yandex.net/";


    public static ICloudClient getApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ICloudClient iCloudClient = retrofit.create(ICloudClient.class);
        return iCloudClient;

    }
}


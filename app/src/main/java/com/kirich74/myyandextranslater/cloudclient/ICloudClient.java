package com.kirich74.myyandextranslater.cloudclient;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Kirill Pilipenko on 13.04.2017.
 */

public interface ICloudClient {


    @GET("/api/v1.5/tr.json/translate")
    Call<TranlateModel> getTranslate(@Query("key") String key, @Query("text") String text,
            @Query("lang") String lang);

    @GET("/api/v1.5/tr.json/getLangs")
    Call<LanguagesModel> getLangs(@Query("key") String key, @Query("ui") String lang);

}
package com.d.end_test_murphy.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final String END_API = "https://www.endclothing.com/media/catalog/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(END_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}

package com.d.end_test_murphy.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ENDApi {
    @GET("example.json")
    Call<ProductList> getProducts();
}

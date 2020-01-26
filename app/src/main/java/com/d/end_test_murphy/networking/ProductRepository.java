package com.d.end_test_murphy.networking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.d.end_test_murphy.model.ProductList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    private static final String TAG = "ProductRepository";

    private static ProductRepository productRepository;
    private ENDApi endApi;

    public static ProductRepository getInstance() {
        if(productRepository == null) {
            productRepository = new ProductRepository();
        }
        return productRepository;
    }

    public ProductRepository() {
        endApi = RetrofitService.createService(ENDApi.class);
    }

    public MutableLiveData<ProductList> getProductList() {
        final MutableLiveData<ProductList> productList = new MutableLiveData<>();
        endApi.getProducts().enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                if(!response.isSuccessful()) {
                    Log.d(TAG, "HTTP code: " + response.code());
                }
                productList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                Log.d(TAG, "Retrofit throwable message: " + t.getMessage());
            }
        });
        return productList;
    }
}

package com.d.end_test_murphy.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.d.end_test_murphy.model.ENDApi;
import com.d.end_test_murphy.model.Product;
import com.d.end_test_murphy.model.ProductList;
import com.d.end_test_murphy.R;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String END_API = "https://www.endclothing.com/media/catalog/";

    public static String TAG = "MainActivity";

    private ENDApi endApi;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private List<Product> products;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // How much of this do I need?
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(END_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        endApi = retrofit.create(ENDApi.class);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        adapter = new ProductAdapter(this);
        recyclerView.setAdapter(adapter);

        // HEADER view type needs span size of 2
        gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(adapter.getItemViewType(position)) {
                    case 0:
                        return 2;
                    default:
                        return 1;
                }
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);

        getENDProducts();
    }

    private void getENDProducts() {
        Call<ProductList> call = endApi.getProducts();

        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                // check if HTTP code is between 200-300
                if (!response.isSuccessful()) {
                    Log.d(TAG,"Code: " + response.code());
                    return;
                }
                products = response.body().getProducts();
                recyclerView.setAdapter(adapter);
                adapter.setProducts(products);
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                Log.d(TAG,"Message: " + t.getMessage());
            }
        });
    }

    public void onButtonClick(View view) {
        //TODO
    }
}

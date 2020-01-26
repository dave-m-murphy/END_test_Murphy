package com.d.end_test_murphy.view;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.d.end_test_murphy.networking.RetrofitService;
import com.d.end_test_murphy.networking.ENDApi;
import com.d.end_test_murphy.model.Product;
import com.d.end_test_murphy.model.ProductList;
import com.d.end_test_murphy.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String TAG = "MainActivity";
    private DrawerLayout drawerLayout;

    private ENDApi endApi;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        endApi = RetrofitService.createService(ENDApi.class);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        adapter = new ProductAdapter(this);
        recyclerView.setAdapter(adapter);

        // HEADER view type needs span size of 2
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
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

        drawerLayout = findViewById(R.id.drawer_layout);
        TextView filterTextView = findViewById(R.id.product_filter);
        filterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Toast.makeText(getApplicationContext(), product.getName() + " selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onNavHeaderCloseClick(View view) {
        drawerLayout.closeDrawer(GravityCompat.END);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //TODO
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
}

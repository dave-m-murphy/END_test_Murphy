package com.d.end_test_murphy.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.d.end_test_murphy.viewmodels.ProductViewModel;
import com.d.end_test_murphy.model.Product;
import com.d.end_test_murphy.model.ProductList;
import com.d.end_test_murphy.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
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

        ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.init();
        productViewModel.getProductListRepository().observe(this, new Observer<ProductList>() {
            @Override
            public void onChanged(ProductList productList) {
                adapter.setProducts(productList.getProducts());
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

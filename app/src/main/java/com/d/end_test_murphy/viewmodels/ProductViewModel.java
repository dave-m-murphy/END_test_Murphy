package com.d.end_test_murphy.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.d.end_test_murphy.model.ProductList;
import com.d.end_test_murphy.networking.ProductRepository;

public class ProductViewModel extends ViewModel {

    private MutableLiveData<ProductList> mutableLiveData;

    public void init() {
        if(mutableLiveData != null) {
            return;
        }
        ProductRepository productRepository = ProductRepository.getInstance();
        mutableLiveData = productRepository.getProductList();
    }

    public LiveData<ProductList> getProductListRepository() {
        return mutableLiveData;
    }
}

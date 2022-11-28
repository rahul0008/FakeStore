package com.example.fakestore.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fakestore.server.entity.Product
import com.example.fakestore.server.handlers.RepositoryCallback
import com.example.fakestore.server.handlers.RequestProduct
import com.example.fakestore.server.handlers.ResultProduct
import com.example.fakestore.server.repository.ProductRepository

class MainActivityViewModel : ViewModel() {

    val TAG = "MainActivityViewModel"
    var productRepository = ProductRepository()
    private var _products: MutableLiveData<ArrayList<Product>> = MutableLiveData()
    private var _message: MutableLiveData<String> = MutableLiveData()
    private var _productsCategory: MutableLiveData<ArrayList<Product>> = MutableLiveData()

    var products: LiveData<ArrayList<Product>> = _products
    var productCategory: LiveData<ArrayList<Product>> = _productsCategory
    var message: LiveData<String> = _message

    var selectedProduct :Product?=null
    fun fetchProducts() {
        productRepository.fetchProducts(fetchRepositoryCallback)
    }

    private var fetchRepositoryCallback = object : RepositoryCallback<ArrayList<Product>> {

        override fun onComplete(result: Result<ArrayList<Product>>) {
            result.onSuccess {
                Log.i(TAG, "onComplete: receivedData")
                _products.postValue(it as ArrayList<Product>)
            }
        }

        override fun onError(errorMessage: String) {
            _message.postValue(errorMessage)
        }

    }

    fun addProduct(product: RequestProduct,addProductRepositoryCallback:RepositoryCallback<ResultProduct>) {
        productRepository.addProducts(product, addProductRepositoryCallback)
    }

    fun deleteProduct(id: Int?, addProductRepositoryCallback:RepositoryCallback<Product>) {
        id?.let { productRepository.deleteProducts(it, addProductRepositoryCallback) }
    }
    fun updateProduct(id: Int?,requestProduct: RequestProduct, updateProductRepositoryCallback:RepositoryCallback<Product>) {
        id?.let { productRepository.updateProducts(it,requestProduct, updateProductRepositoryCallback) }
    }

    fun removeProduct(product: Product){
        _products.value?.remove(product)
    }
    fun getCategory(products: ArrayList<Product>): ArrayList<String> {
        var categories: ArrayList<String> = ArrayList()
        for (product in products) {
            if (!categories.contains(product.category)) {
                product.category?.let { categories.add(it) }
                Log.i("CategoryObserver", "getCategory: " + product.category)
            }
        }
        return categories
    }

    fun selectedCategory(category: String, products: ArrayList<Product>?) {
        var returnProducts: ArrayList<Product> = ArrayList()
        if (products != null) {
            for (product in products) {
                if (product.category == category)
                    returnProducts.add(product)
            }
        }

        _productsCategory.postValue(returnProducts)
    }

}
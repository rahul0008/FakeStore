package com.example.fakestore.server.repository

import com.example.fakestore.server.entity.Product
import com.example.fakestore.server.handlers.RepositoryCallback
import com.example.fakestore.server.handlers.RequestProduct
import com.example.fakestore.server.handlers.ResultProduct
import com.example.fakestore.server.networkCalls.ProductsApi
import com.example.fakestore.utils.Nomenclature
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProductRepository {
    var productsApi: ProductsApi

    init {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Nomenclature.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productsApi = retrofit.create(ProductsApi::class.java)
    }


    fun fetchProducts(repositoryCallback: RepositoryCallback<ArrayList<Product>>) {
        productsApi.fetchProducts().enqueue(
            object : Callback<ArrayList<Product>> {
                override fun onResponse(
                    call: Call<ArrayList<Product>>,
                    response: Response<ArrayList<Product>>
                ) {
                    if (response.isSuccessful) {
                        val body: ArrayList<Product>? = response.body()
                        val result: Result<ArrayList<Product>> =
                            Result.success(body) as Result<ArrayList<Product>>
                        repositoryCallback.onComplete(result)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                    t.message?.let { repositoryCallback.onError(it) }
                }

            }
        )
    }


    fun addProducts(
        product: RequestProduct,
        repositoryCallback: RepositoryCallback<ResultProduct>
    ) {
        val jsonObject = JSONObject()
        jsonObject.put("title", product.title)
        jsonObject.put("price", product.price)
        jsonObject.put("description: ", product.description)
        jsonObject.put("image: ", product.image)
        jsonObject.put("category: ", product.category)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        val request =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObjectString)

        productsApi.addProduct(request).enqueue(
            object : Callback<ResultProduct> {
                override fun onResponse(
                    call: Call<ResultProduct>,
                    response: Response<ResultProduct>
                ) {
                    if (response.isSuccessful) {
                        val body: ResultProduct? = response.body()
                        val result: Result<ResultProduct> =
                            Result.success(body) as Result<ResultProduct>
                        repositoryCallback.onComplete(result)
                    }
                }

                override fun onFailure(call: Call<ResultProduct>, t: Throwable) {
                    t.message?.let { repositoryCallback.onError(it) }
                }

            }
        )
    }


    fun deleteProducts(id: Int, repositoryCallback: RepositoryCallback<Product>) {
        productsApi.deleteProduct(id).enqueue(
            object : Callback<Product> {
                override fun onResponse(
                    call: Call<Product>,
                    response: Response<Product>
                ) {
                    if (response.isSuccessful) {
                        val body: Product? = response.body()
                        val result: Result<Product> = Result.success(body) as Result<Product>
                        repositoryCallback.onComplete(result)
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    t.message?.let { repositoryCallback.onError(it) }
                }

            }
        )

    }

    fun updateProducts(
        id: Int,
        product: RequestProduct,
        repositoryCallback: RepositoryCallback<Product>
    ) {

        val jsonObject = JSONObject()
        jsonObject.put("title", product.title)
        jsonObject.put("price", product.price)
        jsonObject.put("description: ", product.description)
        jsonObject.put("image: ", product.image)
        jsonObject.put("category: ", product.category)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        val request =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObjectString)
        productsApi.updateProduct(id, request).enqueue(
            object : Callback<Product> {
                override fun onResponse(
                    call: Call<Product>,
                    response: Response<Product>
                ) {
                    if (response.isSuccessful) {
                        val body: Product? = response.body()
                        val result: Result<Product> = Result.success(body) as Result<Product>
                        repositoryCallback.onComplete(result)
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    t.message?.let { repositoryCallback.onError(it) }
                }

            }
        )

    }
}
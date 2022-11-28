package com.example.fakestore.server.networkCalls

import com.example.fakestore.server.entity.Product
import com.example.fakestore.server.handlers.ResultProduct
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ProductsApi {

    @GET("/products")
    fun fetchProducts(): Call<ArrayList<Product>>

    @POST("/products")
    fun addProduct(@Body requestBody: RequestBody): Call<ResultProduct>

    @DELETE("/products/{id}")
    fun deleteProduct(@Path("id") id: Int): Call<Product>

    @PUT("/products/{id}")
    fun updateProduct(@Path("id") id: Int, @Body requestBody: RequestBody): Call<Product>

}
package com.example.fakestore.server.networkCalls

import com.example.fakestore.server.entity.Product
import com.example.fakestore.server.handlers.ResultProduct
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductsApi {

    @GET("/products")
    fun fetchProducts():Call<ArrayList<Product>>

    @POST("/")
    fun addProduct(@Body requestBody: RequestBody):Response<ResultProduct>

}
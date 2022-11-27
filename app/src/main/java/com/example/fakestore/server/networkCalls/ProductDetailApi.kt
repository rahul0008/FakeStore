package com.example.fakestore.server.networkCalls

import retrofit2.http.GET

interface ProductDetailApi {
    @GET()
    fun fetchProductDetails(){}
}
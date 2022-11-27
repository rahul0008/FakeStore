package com.example.fakestore.utils

class Nomenclature {
    companion object{
        val BaseUrl ="https://fakestoreapi.com"
        val UpdateUrl = "https://dummyjson.com/products"
    }

    enum class messageType {
        None,
        Toast,
        Dialog,
        SnakeBar
    }
}
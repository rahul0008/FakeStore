package com.example.fakestore.server.handlers

import com.example.fakestore.server.entity.Product

interface RepositoryCallback<T> {
    fun onComplete(result: Result<T>)
    fun onError(errorMessage :String)
}
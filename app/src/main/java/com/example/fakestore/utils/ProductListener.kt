package com.example.fakestore.utils

import com.example.fakestore.server.entity.Product

interface ProductListener {
    fun isSelected(product: Product)
}
package com.example.finalproject.cart

import com.example.finalproject.model.Produk

object CartManager {
    private val cartList = mutableListOf<Produk>()

    fun addToCart(produk: Produk) {
        cartList.add(produk)
    }

    fun getCart(): List<Produk> = cartList

    fun clearCart() {
        cartList.clear()
    }
}
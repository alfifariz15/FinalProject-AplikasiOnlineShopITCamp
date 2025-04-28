package com.example.finalproject.network

import com.example.finalproject.model.Produk
import retrofit2.Call
import retrofit2.http.GET

interface APIServices {
    @GET("products?limit=20")
    fun getProduk(): Call<List<Produk>>
}

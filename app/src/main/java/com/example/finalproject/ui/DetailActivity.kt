package com.example.finalproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ActivityDetailBinding
import com.example.finalproject.model.Produk
import android.widget.Toast
import com.example.finalproject.cart.CartManager

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val produk = intent.getParcelableExtra<Produk>("produk")

        binding.btnAddToCart.setOnClickListener {
            produk?.let {
                CartManager.addToCart(it)
                Toast.makeText(this, "Ditambahkan ke Keranjang", Toast.LENGTH_SHORT).show()
            }
        }

        if (produk != null) {
            binding.tvNamaDetail.text = produk.title
            binding.tvHargaDetail.text = "$${produk.price}"
            binding.tvDescDetail.text = produk.description
            Glide.with(this)
                .load(produk.image)
                .into(binding.imgDetail)
        }
    }
}
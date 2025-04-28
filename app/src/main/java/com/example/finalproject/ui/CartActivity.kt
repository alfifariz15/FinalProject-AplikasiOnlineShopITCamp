package com.example.finalproject.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.adapter.ProdukAdapter
import com.example.finalproject.cart.CartManager
import com.example.finalproject.databinding.ActivityCartBinding
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.finalproject.model.Produk

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var produkAdapter: ProdukAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cartList = CartManager.getCart()

        produkAdapter = ProdukAdapter(cartList)
        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = produkAdapter
            itemAnimator = DefaultItemAnimator()
        }

        updateTotalHarga(cartList)

        binding.btnCheckout.setOnClickListener {
            if (cartList.isNotEmpty()) {
                showCheckoutDialog()
            } else {
                Toast.makeText(this, "Keranjang kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateTotalHarga(cartList: List<Produk>) {
        val total = cartList.sumOf { it.price }
        binding.tvTotalHarga.text = "Total: $${String.format("%,.0f", total)}"
    }

    private fun showCheckoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Checkout")
            .setMessage("Apakah Anda yakin ingin checkout?")
            .setPositiveButton("Ya") { _, _ ->
                CartManager.clearCart()
                Toast.makeText(this, "Checkout berhasil!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
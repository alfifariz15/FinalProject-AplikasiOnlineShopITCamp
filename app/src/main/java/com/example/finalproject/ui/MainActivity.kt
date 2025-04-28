package com.example.finalproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.adapter.ProdukAdapter
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.model.Produk
import com.example.finalproject.network.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

    class MainActivity : AppCompatActivity() {

        private lateinit var binding: ActivityMainBinding
        private lateinit var produkAdapter: ProdukAdapter
        private var produkList = listOf<Produk>() // List produk full

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            setupSpinner()
            binding.btnCart.setOnClickListener {
                startActivity(Intent(this, CartActivity::class.java))
            }

            fetchProduk()
        }

        private fun setupSpinner() {
            val kategoriList = listOf("Product List", "electronics", "jewelery", "men's clothing", "women's clothing")

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, kategoriList)
            binding.spinnerKategori.adapter = adapter

            binding.spinnerKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedKategori = kategoriList[position]
                    filterProduk(selectedKategori)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }

        private fun fetchProduk() {
            APIClient.instance.getProduk().enqueue(object : Callback<List<Produk>> {
                override fun onResponse(call: Call<List<Produk>>, response: Response<List<Produk>>) {
                    if (response.isSuccessful) {
                        produkList = response.body() ?: listOf()
                        produkAdapter = ProdukAdapter(produkList)
                        binding.rvProduk.apply {
                            layoutManager = GridLayoutManager(this@MainActivity, 2)
                            adapter = produkAdapter
                        }
                    }
                }

                override fun onFailure(call: Call<List<Produk>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

        private fun filterProduk(kategori: String) {
            val filteredList = if (kategori == "All") {
                produkList
            } else {
                produkList.filter { it.category.equals(kategori, ignoreCase = true) }
            }

            produkAdapter = ProdukAdapter(filteredList)
            binding.rvProduk.adapter = produkAdapter
        }
    }


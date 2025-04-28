package com.example.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ItemProdukBinding
import com.example.finalproject.model.Produk
import android.content.Intent
import com.example.finalproject.ui.DetailActivity

class ProdukAdapter(private val listProduk: List<Produk>) : RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemProdukBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listProduk.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produk = listProduk[position]
        holder.binding.apply {
            tvNamaProduk.text = produk.title
            tvHargaProduk.text = "$${produk.price}"
            Glide.with(imgProduk.context)
                .load(produk.image)
                .into(imgProduk)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("produk", produk)
            context.startActivity(intent)
        }

    }
}

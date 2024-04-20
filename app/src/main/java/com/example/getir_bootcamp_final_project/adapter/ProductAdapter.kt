package com.example.getir_bootcamp_final_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getir_bootcamp_final_project.databinding.ItemProductSmallBinding
import com.example.getir_bootcamp_final_project.model.Product

class ProductAdapter(
    val productList: List<Product> = emptyList()
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductSmallBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.tvProductName.text = productList[position].name
        holder.binding.tvProductAttribute.text = productList[position].attribute
        holder.binding.tvProductPrice.text = productList[position].price
    }

    override fun getItemCount() = productList.size
}
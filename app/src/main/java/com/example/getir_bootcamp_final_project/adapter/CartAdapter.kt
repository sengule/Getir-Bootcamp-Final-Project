package com.example.getir_bootcamp_final_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getir_bootcamp_final_project.databinding.ItemCartBinding
import com.example.getir_bootcamp_final_project.model.Product

class CartAdapter(
    val productList: List<Product> = emptyList()
): RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.binding.tvProductName.text = productList[position].name
        holder.binding.tvProductAttribute.text = productList[position].attribute
        holder.binding.tvProductPrice.text = productList[position].price
    }

    override fun getItemCount() = productList.size

}
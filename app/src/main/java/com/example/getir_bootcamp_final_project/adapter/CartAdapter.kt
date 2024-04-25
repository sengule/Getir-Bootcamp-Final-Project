package com.example.getir_bootcamp_final_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.getir_bootcamp_final_project.databinding.ItemCartBinding
import com.example.getir_bootcamp_final_project.model.Product
import com.example.getir_bootcamp_final_project.utils.handleProductImageUrl

class CartAdapter(
    private val onIncButtonClicked: (Product, quantityText: TextView, ImageView) -> Unit = { _, _, _->},
    private val onDecButtonClicked: (Product, quantityText: TextView, ImageView) -> Unit = { _, _, _ ->}
): RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    var productList: List<Product> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        with(holder.binding){
            tvProductName.text = productList[position].name
            tvProductAttribute.text = productList[position].attribute
            tvProductPrice.text = productList[position].priceText
            tvProductQuantity.text = productList[position].quantity.toString()

            cvIncrementContainer.setOnClickListener {
                onIncButtonClicked(productList[position], tvProductQuantity, ivDecrementIcon)
            }

            cvDecrementContainer.setOnClickListener {
                onDecButtonClicked(productList[position], tvProductQuantity, ivDecrementIcon)
            }
        }

        val imageUrl = handleProductImageUrl(productList[position])
        Glide.with(holder.itemView.context).load(imageUrl).centerCrop().into(holder.binding.ivProductImage)

    }

    override fun getItemCount() = productList.size

}
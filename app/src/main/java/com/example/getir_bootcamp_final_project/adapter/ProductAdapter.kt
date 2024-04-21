package com.example.getir_bootcamp_final_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getir_bootcamp_final_project.databinding.ItemProductLargeBinding
import com.example.getir_bootcamp_final_project.databinding.ItemProductSmallBinding
import com.example.getir_bootcamp_final_project.model.Product


class ProductAdapter(
    private val productList: List<Product> = emptyList(),
    private val viewType: Int = ITEM_SMALL
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_SMALL = 1
        const val ITEM_LARGE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_SMALL -> {
                val binding = ItemProductSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProductSmallViewHolder(binding)
            }
            ITEM_LARGE -> {
                val binding = ItemProductLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProductLargeViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = productList[position]
        when (holder.itemViewType) {
            ITEM_SMALL -> {
                val smallViewHolder = holder as ProductSmallViewHolder
                smallViewHolder.binding.tvProductName.text = item.name
                smallViewHolder.binding.tvProductAttribute.text = item.attribute
                smallViewHolder.binding.tvProductPrice.text = item.price
            }
            ITEM_LARGE -> {
                val largeViewHolder = holder as ProductLargeViewHolder
                largeViewHolder.binding.tvProductName.text = item.name
                largeViewHolder.binding.tvProductAttribute.text = item.attribute
                largeViewHolder.binding.tvProductPrice.text = item.price
            }
        }
    }

    override fun getItemCount() = productList.size

    //This methods override given viewType with recyclerview viewType
    //So if user give ITEM_LARGE, it will be Large item printed on the screen
    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    inner class ProductSmallViewHolder(val binding: ItemProductSmallBinding) : RecyclerView.ViewHolder(binding.root)

    inner class ProductLargeViewHolder(val binding: ItemProductLargeBinding) : RecyclerView.ViewHolder(binding.root)
}

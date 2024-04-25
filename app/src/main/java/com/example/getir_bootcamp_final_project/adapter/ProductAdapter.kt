package com.example.getir_bootcamp_final_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.getir_bootcamp_final_project.R
import com.example.getir_bootcamp_final_project.databinding.ItemProductLargeBinding
import com.example.getir_bootcamp_final_project.databinding.ItemProductSmallBinding
import com.example.getir_bootcamp_final_project.model.Product
import com.example.getir_bootcamp_final_project.utils.handleProductImageUrl

class ProductAdapter(
    private val viewType: Int = ITEM_SMALL,
    private val onProductClick:
        (Product, View) -> Unit = {_,_->},
    private val onAddButtonClicked:
        (Product, buttonContainer: View, quantityText: TextView, icon: ImageView) -> Unit = {_,_,_,_->},
    private val onRemoveButtonClicked:
        (Product, buttonContainer: View, quantityText: TextView, icon: ImageView) -> Unit = {_,_,_,_->}
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var productList: List<Product> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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

                with(smallViewHolder.binding){
                    tvProductName.text = item.name
                    tvProductAttribute.text = item.attribute
                    tvProductPrice.text = item.priceText

                    cvAddButton.setOnClickListener {
                        llExpandableContent.visibility = View.VISIBLE
                        onAddButtonClicked(item, cvButtonContainer, tvProductQuantity, ivDecrementIcon)
                    }

                    cvDecrementContainer.setOnClickListener {
                        onRemoveButtonClicked(item, llExpandableContent, tvProductQuantity, ivDecrementIcon)
                    }

                    val ic = if (tvProductQuantity.text == "1") R.drawable.ic_trash else R.drawable.ic_minus
                    val placeHolder = ivDecrementIcon.context.resources.getDrawable(ic, null)
                    ivDecrementIcon.setImageDrawable(placeHolder)

                    llProductContainer.setOnClickListener {
                        onProductClick(item, it)
                    }
                }

                val imageUrl = handleProductImageUrl(item)
                Glide.with(holder.itemView.context).load(imageUrl).centerCrop().into(smallViewHolder.binding.ivProductImage)
            }
            ITEM_LARGE -> {
                val largeViewHolder = holder as ProductLargeViewHolder

                with(largeViewHolder.binding){
                    tvProductName.text = item.name
                    tvProductAttribute.text = item.attribute
                    tvProductPrice.text = item.priceText

                    cvAddButton.setOnClickListener {
                        llExpandableContent.visibility = View.VISIBLE
                        onAddButtonClicked(item, cvButtonContainer, tvProductQuantity, ivDecrementIcon)
                    }

                    cvDecrementContainer.setOnClickListener {
                        onRemoveButtonClicked(item, llExpandableContent, tvProductQuantity, ivDecrementIcon)
                    }

                    val ic = if (tvProductQuantity.text == "1") R.drawable.ic_trash else R.drawable.ic_minus
                    val placeHolder = ivDecrementIcon.context.resources.getDrawable(ic, null)
                    ivDecrementIcon.setImageDrawable(placeHolder)

                    llProductContainer.setOnClickListener {
                        onProductClick(item, it)
                    }
                }

                val imageUrl = handleProductImageUrl(item)
                Glide.with(holder.itemView.context).load(imageUrl).centerCrop().into(largeViewHolder.binding.ivProductImage)
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

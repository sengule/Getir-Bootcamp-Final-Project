package com.example.getir_bootcamp_final_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getir_bootcamp_final_project.adapter.ProductAdapter
import com.example.getir_bootcamp_final_project.databinding.FragmentBasketBinding
import com.example.getir_bootcamp_final_project.databinding.FragmentProductListingBinding
import com.example.getir_bootcamp_final_project.utils.MarginItemDecorator
import com.example.getir_bootcamp_final_project.utils.productList

class ProductListingFragment : Fragment() {

    private var _binding: FragmentProductListingBinding? = null
    private val binding get() = _binding!!

    private lateinit var productsHorizontalRecyclerView: RecyclerView
    private lateinit var productsVerticalRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProductListingBinding.inflate(inflater, container, false)
        productsVerticalRecyclerView = binding.rvProductsVertical
        productsHorizontalRecyclerView = binding.rvProductsHorizontal
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridLayoutManager = GridLayoutManager(this.context, 3)

        val horizontalLayoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)

        productsHorizontalRecyclerView.layoutManager = horizontalLayoutManager
        productsHorizontalRecyclerView.adapter = ProductAdapter(productList)
        val margin = resources.getDimensionPixelSize(R.dimen.margin_start)
        val itemDecoration = MarginItemDecorator(margin)
        productsHorizontalRecyclerView.addItemDecoration(itemDecoration)

        val newList = productList + productList + productList + productList

        productsVerticalRecyclerView.layoutManager = gridLayoutManager
        productsVerticalRecyclerView.adapter = ProductAdapter(newList, ProductAdapter.ITEM_LARGE)

    }

}
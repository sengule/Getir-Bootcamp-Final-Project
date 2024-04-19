package com.example.getir_bootcamp_final_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getir_bootcamp_final_project.adapter.CartAdapter
import com.example.getir_bootcamp_final_project.databinding.FragmentBasketBinding
import com.example.getir_bootcamp_final_project.databinding.FragmentDetailBinding
import com.example.getir_bootcamp_final_project.utils.DividerItemDecorator
import com.example.getir_bootcamp_final_project.utils.productList

class BasketFragment : Fragment() {

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartProductRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        cartProductRecyclerView = binding.rvCartProducts
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)

        cartProductRecyclerView.layoutManager = layoutManager
        cartProductRecyclerView.adapter = CartAdapter(productList)
        val itemDecoration = DividerItemDecorator(ContextCompat.getDrawable(this.requireContext(), R.drawable.divider_cart_product)!!)
        cartProductRecyclerView.addItemDecoration(itemDecoration)
    }

}
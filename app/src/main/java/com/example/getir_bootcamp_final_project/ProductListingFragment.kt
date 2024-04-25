package com.example.getir_bootcamp_final_project

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getir_bootcamp_final_project.adapter.ProductAdapter
import com.example.getir_bootcamp_final_project.databinding.FragmentProductListingBinding
import com.example.getir_bootcamp_final_project.model.Product
import com.example.getir_bootcamp_final_project.utils.GridItemDecorator
import com.example.getir_bootcamp_final_project.utils.ItemDecorator
import com.example.getir_bootcamp_final_project.utils.formatDouble
import com.example.getir_bootcamp_final_project.utils.productList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListingFragment : Fragment() {

    private var _binding: FragmentProductListingBinding? = null
    private val binding get() = _binding!!

    private lateinit var productsHorizontalRecyclerView: RecyclerView
    private lateinit var productsVerticalRecyclerView: RecyclerView

    private val productsViewModel: ProductsViewModel by activityViewModels()

    private lateinit var horizontalAdapter: ProductAdapter
    private lateinit var verticalAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapters()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProductListingBinding.inflate(inflater, container, false)
        productsVerticalRecyclerView = binding.rvProductsVertical
        productsHorizontalRecyclerView = binding.rvProductsHorizontal

        val horizontalLayoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        val margin = resources.getDimensionPixelSize(R.dimen.dimen_16dp)
        val itemDecoration = ItemDecorator(margin, margin)


        productsHorizontalRecyclerView.apply {
            layoutManager = horizontalLayoutManager
            adapter = horizontalAdapter
            addItemDecoration(itemDecoration)
        }

        val newList = productList + productList + productList + productList
        val gridMargin = resources.getDimensionPixelSize(R.dimen.dimen_10dp)
        val gridDecorator = GridItemDecorator(3, gridMargin)
        val gridLayoutManager = GridLayoutManager(this.context, 3)
        verticalAdapter.productList = newList

        productsVerticalRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = verticalAdapter
            addItemDecoration(gridDecorator)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llCart.setOnClickListener {
            if (productsViewModel.cartUiState.value.products.isEmpty()){
                Toast.makeText(requireContext(), "Sepet Boş", Toast.LENGTH_SHORT).show()
            }else{
                Navigation.findNavController(it).navigate(R.id.action_productListingFragment_to_basketFragment)
            }
        }

        lifecycleScope.launch {
            productsViewModel.cartUiState.collect { cartState ->
                val totalString = "₺"+cartState.total.formatDouble(2).replace('.',',')
                binding.tvTotalPrice.text = totalString
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                productsViewModel.productUiState.collect { uiState ->
                    when (uiState) {
                        is ProductUiState.Loading -> {

                        }
                        is ProductUiState.Success -> {
                            verticalAdapter.productList = uiState.products
                            horizontalAdapter.productList = uiState.suggestedProducts
                        }
                        is ProductUiState.Error -> {

                        }
                        is ProductUiState.Idle->{

                        }
                    }
                }
            }
        }
    }

    private fun initAdapters(){
        val onAddButtonClicked: (Product, View, TextView, ImageView) -> Unit = { addedProduct, llExpandableContent, quantityText, ic ->
            var placeHolder: Drawable? = null
            if (addedProduct.quantity == 0){
                llExpandableContent.visibility = View.VISIBLE
                placeHolder = requireContext().resources.getDrawable(R.drawable.ic_trash, null)
                ic.setImageDrawable(placeHolder)
            }else{
                placeHolder = requireContext().resources.getDrawable(R.drawable.ic_minus, null)
            }

            ic.setImageDrawable(placeHolder)

            if (addedProduct in productsViewModel.cartUiState.value.products){
                productsViewModel.incrementProductQuantity(addedProduct)
            }else{
                productsViewModel.addProductToCart(addedProduct)
            }

            quantityText.text = addedProduct.quantity.toString()
        }

        val onRemoveButtonClicked :  (Product, View, TextView, ImageView) -> Unit = { removedProduct, llExpandableContent, quantityText, ic->
            if (removedProduct.quantity == 1){
                llExpandableContent.visibility = View.GONE
                productsViewModel.removeProductFromCart(removedProduct)
            }else{
                productsViewModel.decrementProductQuantity(removedProduct)
            }

            quantityText.text = removedProduct.quantity.toString()

        }

        horizontalAdapter = ProductAdapter(
            onProductClick = { selectedProduct, view->
                productsViewModel.setSelectedProduct(selectedProduct)
                Navigation.findNavController(view).navigate(R.id.action_productListingFragment_to_detailFragment)
            },
            onAddButtonClicked = {addedProduct, buttonContainer, quantityText, ic->
                onAddButtonClicked(addedProduct, buttonContainer, quantityText, ic)
            },
            onRemoveButtonClicked = {removedProduct, buttonContainer, quantityText, ic->
                onRemoveButtonClicked(removedProduct, buttonContainer, quantityText, ic)
            }
        )

        verticalAdapter = ProductAdapter(
            ProductAdapter.ITEM_LARGE,
            onProductClick = { selectedProduct, view->
                productsViewModel.setSelectedProduct(selectedProduct)
                Navigation.findNavController(view).navigate(R.id.action_productListingFragment_to_detailFragment)
            },
            onAddButtonClicked = {addedProduct, buttonContainer, quantityText, ic->
                onAddButtonClicked(addedProduct, buttonContainer, quantityText, ic)
            },
            onRemoveButtonClicked = {removedProduct, buttonContainer, quantityText, ic->
                onRemoveButtonClicked(removedProduct, buttonContainer, quantityText, ic)
            }
        )
    }
}
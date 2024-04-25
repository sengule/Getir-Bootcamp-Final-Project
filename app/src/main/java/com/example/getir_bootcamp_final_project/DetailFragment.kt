package com.example.getir_bootcamp_final_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.getir_bootcamp_final_project.databinding.FragmentDetailBinding
import com.example.getir_bootcamp_final_project.utils.formatDouble
import com.example.getir_bootcamp_final_project.utils.handleProductImageUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val productsViewModel: ProductsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()

        with(binding){
            lifecycleScope.launch {

                launch {
                    productsViewModel.cartUiState.collect{
                        if (it.products.isEmpty()){
                            binding.llCartContainer.visibility = View.GONE
                        }else{
                            binding.llCartContainer.visibility = View.VISIBLE
                        }

                        binding.tvTotalPrice.text = "₺"+it.total.formatDouble(2).replace('.',',')
                    }
                }

                launch {
                    productsViewModel.selectedProduct.collect{
                        val imageUrl = handleProductImageUrl(it)
                        Glide.with(requireContext()).load(imageUrl).centerCrop().into(ivProductImage)

                        tvProductPrice.text = "₺"+it.price.formatDouble(2).replace('.',',')
                        tvProductName.text = it.name
                        tvProductAttribute.text = it.attribute

                        toggleButtons(it.quantity)
                    }
                }

            }
        }
    }

    private fun setClickListeners(){
        binding.btnAddCart.setOnClickListener {
            productsViewModel.addProductToCart(productsViewModel.selectedProduct.value)
            toggleButtons(productsViewModel.selectedProduct.value.quantity)
            binding.tvProductQuantity.text = productsViewModel.selectedProduct.value.quantity.toString()
        }

        binding.cvIncrementContainer.setOnClickListener {
            productsViewModel.incrementProductQuantity(productsViewModel.selectedProduct.value)
            binding.tvProductQuantity.text = productsViewModel.selectedProduct.value.quantity.toString()
        }

        binding.cvDecrementContainer.setOnClickListener {

            if (productsViewModel.selectedProduct.value.quantity == 1){
                productsViewModel.removeProductFromCart(productsViewModel.selectedProduct.value)
            }else{
                productsViewModel.decrementProductQuantity(productsViewModel.selectedProduct.value)
            }

            toggleButtons(productsViewModel.selectedProduct.value.quantity)

            binding.tvProductQuantity.text = productsViewModel.selectedProduct.value.quantity.toString()
        }

        binding.topAppBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.llCartContainer.setOnClickListener {
            parentFragmentManager.popBackStack()
            Navigation.findNavController(it).navigate(R.id.action_detailFragment_to_basketFragment)
        }
    }

    private fun toggleButtons(quantity: Int){
        if (quantity > 0){
            binding.btnAddCart.visibility = View.GONE
            binding.cvButtonContainer. visibility = View.VISIBLE
        }else{
            binding.btnAddCart.visibility = View.VISIBLE
            binding.cvButtonContainer. visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
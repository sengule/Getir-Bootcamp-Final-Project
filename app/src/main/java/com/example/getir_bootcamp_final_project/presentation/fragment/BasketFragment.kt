package com.example.getir_bootcamp_final_project.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getir_bootcamp_final_project.presentation.ProductsViewModel
import com.example.getir_bootcamp_final_project.R
import com.example.getir_bootcamp_final_project.adapter.CartAdapter
import com.example.getir_bootcamp_final_project.databinding.FragmentBasketBinding
import com.example.getir_bootcamp_final_project.utils.DividerItemDecorator
import com.example.getir_bootcamp_final_project.utils.applyDiscount
import com.example.getir_bootcamp_final_project.utils.clearBackStack
import com.example.getir_bootcamp_final_project.utils.formatDouble
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketFragment : Fragment() {

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartProductRecyclerView: RecyclerView

    private val productsViewModel: ProductsViewModel by activityViewModels()

    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartAdapter = CartAdapter(
            onIncButtonClicked = { incProduct, quantityText, decrementIcon->
                val placeHolder = requireContext().resources.getDrawable(R.drawable.ic_minus, null)
                decrementIcon.setImageDrawable(placeHolder)
                productsViewModel.incrementProductQuantity(incProduct)
                quantityText.text = incProduct.quantity.toString()
            },
            onDecButtonClicked = { decProduct, quantityText, decrementIcon->

                if (decProduct.quantity == 1) {
                    productsViewModel.removeProductFromCart(decProduct)
                } else {
                    val placeHolder = if (decProduct.quantity == 2) {
                        requireContext().resources.getDrawable(R.drawable.ic_trash, null)
                    } else {
                        requireContext().resources.getDrawable(R.drawable.ic_minus, null)
                    }
                    decrementIcon.setImageDrawable(placeHolder)
                    productsViewModel.decrementProductQuantity(decProduct)
                }

                quantityText.text = decProduct.quantity.toString()
            }
        )

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
        val verticalLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
        val horizontalLayoutManager = LinearLayoutManager(this.context)
        horizontalLayoutManager.orientation = RecyclerView.HORIZONTAL

        cartProductRecyclerView.layoutManager = verticalLayoutManager
        cartProductRecyclerView.adapter = cartAdapter

        val itemDecoration = DividerItemDecorator(ContextCompat.getDrawable(this.requireContext(),
            R.drawable.divider_cart_product
        )!!)
        cartProductRecyclerView.addItemDecoration(itemDecoration)
        cartProductRecyclerView.isNestedScrollingEnabled = false

        setClickListeners()

        lifecycleScope.launch {
            productsViewModel.cartUiState.collect{cartUiState->
                cartAdapter.productList = cartUiState.products
                binding.tvTotalPrice.text = "₺"+cartUiState.total.formatDouble(2).replace('.',',')
                binding.tvDiscountedPrice.text = "₺"+ applyDiscount(cartUiState.total).formatDouble(2).replace('.',',')
            }
        }

    }

    private fun setClickListeners(){
        binding.topAppBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.ivTrashIcon.setOnClickListener{
            // Clear cart
            productsViewModel.clearCart()
        }

        binding.cvFinishOrderButton.setOnClickListener {
            val navController = Navigation.findNavController(it)

            if (productsViewModel.cartUiState.value.products.isEmpty()){
                Toast.makeText(requireContext(), "Sepet boş olduğundan sipariş tamamlanamadı", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireContext(), "Sipariş Tamamlandı, toplam ücret ₺${productsViewModel.cartUiState.value.total.formatDouble(2)}", Toast.LENGTH_LONG).show()
                productsViewModel.clearCart()
                clearBackStack(parentFragmentManager)
            }

            navController.popBackStack()
        }
    }

}
package com.example.getir_bootcamp_final_project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getir_bootcamp_final_project.domain.ProductUiState
import com.example.getir_bootcamp_final_project.domain.ProductsRepository
import com.example.getir_bootcamp_final_project.model.CartUiState
import com.example.getir_bootcamp_final_project.model.Product
import com.example.getir_bootcamp_final_project.utils.productList
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
): ViewModel() {

    private val _productUiState =  MutableStateFlow<ProductUiState>(ProductUiState.Idle)
    val productUiState = _productUiState.asStateFlow()

    private val _cartUiState = MutableStateFlow<CartUiState>(CartUiState())
    val cartUiState = _cartUiState.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product>(productList[0])
    val selectedProduct = _selectedProduct.asStateFlow()

    var suggestedProducts: List<Product> = emptyList()
        private set

    companion object{
        const val MAIN_PRODUCTS_IX = 0
        const val SUGGESTED_PRODUCTS_IX = 1
    }

    init {
        fetchProductList()
    }

    private fun fetchProductList(){
        _productUiState.value = ProductUiState.Loading

        val productListFlow = productsRepository.getProductList()
        val suggestedProductListFlow = productsRepository.getSuggestedProductList()

        val productsFlow = productListFlow.zip(suggestedProductListFlow){ mainProducts, suggestedProducts->
            val productsPackage = ArrayList<List<Product>>()
            productsPackage.add(MAIN_PRODUCTS_IX, mainProducts)
            productsPackage.add(SUGGESTED_PRODUCTS_IX, suggestedProducts)
            productsPackage
        }

        viewModelScope.launch {
            try {
                productsFlow.collect{ productsPackage->
                    _productUiState.value = ProductUiState.Success(
                        productsPackage[MAIN_PRODUCTS_IX],
                        productsPackage[SUGGESTED_PRODUCTS_IX]
                    )
                    suggestedProducts = productsPackage[SUGGESTED_PRODUCTS_IX]
                }

            }catch (e: Exception){
                _productUiState.value = ProductUiState.Error(e.localizedMessage)
            }
        }
    }

    fun addProductToCart(product: Product){
        product.quantity = 1

        val newProductList: List<Product> = _cartUiState.value.products + product
        val newPrice = _cartUiState.value.total + product.price

        _cartUiState.update {
            it.copy(
                products = newProductList,
                total = newPrice
            )
        }

    }

    fun removeProductFromCart(product: Product){
        product.quantity = 0
        val newProductList: List<Product> = _cartUiState.value.products - product
        val newPrice = _cartUiState.value.total - product.price

        _cartUiState.update {
            it.copy(
                products = newProductList,
                total = newPrice
            )
        }
    }

    fun setSelectedProduct(product: Product){
        _selectedProduct.value = product
    }

    fun incrementProductQuantity(product: Product){
        product.quantity += 1

        _cartUiState.update {
            it.copy(
                total = it.total + product.price
            )
        }
    }

    fun decrementProductQuantity(product: Product){
        product.quantity -= 1

        _cartUiState.update {
            it.copy(
                total = it.total - product.price
            )
        }
    }

    fun clearCart(){
        _cartUiState.update {
            it.copy(
                products = emptyList(),
                total = 0.0
            )
        }
    }

}
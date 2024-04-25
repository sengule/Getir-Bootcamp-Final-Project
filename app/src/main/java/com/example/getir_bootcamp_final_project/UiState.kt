package com.example.getir_bootcamp_final_project

import com.example.getir_bootcamp_final_project.model.Product

sealed class ProductUiState{

    object Idle: ProductUiState()
    object Loading: ProductUiState()
    data class Error(val errorMessage: String?): ProductUiState()
    data class Success(
        val products: List<Product> = emptyList(),
        val suggestedProducts: List<Product> = emptyList()
    ): ProductUiState()

}
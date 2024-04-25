package com.example.getir_bootcamp_final_project.model

data class CartUiState(
    val products: List<Product> = emptyList(),
    val total: Double  = 0.0
)

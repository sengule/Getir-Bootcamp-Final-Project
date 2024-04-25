package com.example.getir_bootcamp_final_project.model

data class ProductInfo(
    val id: String,
    val name: String,
    val productCount: Int,
    val products: List<Product>
)

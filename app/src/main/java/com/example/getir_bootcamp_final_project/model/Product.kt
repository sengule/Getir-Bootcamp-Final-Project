package com.example.getir_bootcamp_final_project.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val priceText: String,
    val attribute: String? = null,
    val shortDescription: String? = null,
    val thumbnailURL: String? = null,
    val imageURL: String? = null,
    val category: String? = null,
    val unitPrice: Double? = null,
    val squareThumbnailURL: String? = null,
    val status: Int? = null,

    var quantity: Int = 0
)


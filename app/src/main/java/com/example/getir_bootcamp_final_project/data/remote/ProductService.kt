package com.example.getir_bootcamp_final_project.data.remote

import com.example.getir_bootcamp_final_project.model.ProductInfo
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductInfo>>

    @GET("suggestedProducts")
    suspend fun getSuggestedProducts(): Response<List<ProductInfo>>
}
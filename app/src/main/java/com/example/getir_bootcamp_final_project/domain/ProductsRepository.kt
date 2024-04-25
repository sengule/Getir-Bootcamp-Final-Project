package com.example.getir_bootcamp_final_project.domain

import com.example.getir_bootcamp_final_project.data.remote.ProductService
import com.example.getir_bootcamp_final_project.model.Product
import com.example.getir_bootcamp_final_project.model.ProductInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response


import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productService: ProductService
) {

    fun getProductList(): Flow<List<Product>> = getProducts {
        productService.getProducts()
    }

    fun getSuggestedProductList(): Flow<List<Product>> = getProducts {
        productService.getSuggestedProducts()
    }

    private fun getProducts(
        apiResponse: suspend () -> Response<List<ProductInfo>>
    ): Flow<List<Product>> = flow {
        val response = apiResponse()

        if (response.isSuccessful){
            val productInfoList = response.body()
            productInfoList?.forEach {info->
                info.products?.let { productList->
                    emit(productList)
                }
            }
        }else{
            // Network error etc
        }
    }.flowOn(Dispatchers.IO)



}
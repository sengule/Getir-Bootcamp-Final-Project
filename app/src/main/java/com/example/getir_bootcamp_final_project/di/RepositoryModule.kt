package com.example.getir_bootcamp_final_project.di

import com.example.getir_bootcamp_final_project.data.remote.ProductService
import com.example.getir_bootcamp_final_project.domain.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideProductRepository(productService: ProductService): ProductsRepository{
        return ProductsRepository(productService)
    }
}
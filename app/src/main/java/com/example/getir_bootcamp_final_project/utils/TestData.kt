package com.example.getir_bootcamp_final_project.utils

import android.graphics.BitmapFactory
import com.example.getir_bootcamp_final_project.R
import com.example.getir_bootcamp_final_project.model.Product

val productList = listOf<Product>(
    Product(
        name = "Product 1",
        price = "₺10.2",
        attribute = "test",
        image = R.drawable.market
    ),
    Product(
        name = "Product 2",
        price = "₺20.1",
        attribute = "test",
        image = R.drawable.market
    ),
    Product(
        name = "Product 3",
        price = "₺30.0",
        attribute = "test",
        image = R.drawable.market
    ),
    Product(
        name = "Product 4",
        price = "₺5.0",
        attribute = "test",
        image = R.drawable.market
    ),
    Product(
        name = "Product 5",
        price = "₺5.1",
        attribute = "test",
        image = R.drawable.market
    )
)
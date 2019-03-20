package com.example.grocerylist

data class Items(val results: List<FoodItem>,
                  val total_pages: Int,
                  val page: Int)
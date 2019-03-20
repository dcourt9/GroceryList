package com.example.grocerylist

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class FoodItemRepository(private val movieDao: FoodItemDao){

    val allMovies: LiveData<List<FoodItem>> = movieDao.getItems()

    @WorkerThread
    fun insert(item: FoodItem){
        movieDao.insertItem(item)
    }

    @WorkerThread
    fun deleteAll(){
        movieDao.DeleteAll()
    }


}
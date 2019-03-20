package com.example.grocerylist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodItemDao{


    @Query("SELECT * FROM grocery_table order BY item_name DESC")
    fun getItems(): LiveData<List<FoodItem>>

    @Query ("DELETE FROM grocery_table")
    fun DeleteAll()

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: FoodItem)


    @Query("SELECT * FROM grocery_table order BY item_name ASC")
    fun getItemsAsc(): LiveData<List<FoodItem>>



}
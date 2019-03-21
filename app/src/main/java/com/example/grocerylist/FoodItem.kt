package com.example.grocerylist


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "grocery_table")
data class FoodItem(@PrimaryKey @ColumnInfo(name ="item_name") var item_name: String,
                     @ColumnInfo(name ="price") var price: Double,
                     @ColumnInfo(name ="quantity") var quantity: Double,
                     //@ColumnInfo(name ="checked") var checked: Boolean,
                     @ColumnInfo(name ="department") var department: String

)
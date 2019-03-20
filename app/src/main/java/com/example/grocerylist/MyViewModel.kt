package com.example.grocerylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MyViewModel(application: Application): AndroidViewModel(application){

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob+ Dispatchers.Main


    private val scope = CoroutineScope(coroutineContext)


    private val repository: FoodItemRepository = FoodItemRepository(ItemRoomDatabase.getDatabase(application).itemDao())

    var allMovies: LiveData<List<FoodItem>>

    private lateinit var sortMovies: LiveData<List<FoodItem>>

    init {
        allMovies = repository.allMovies
    }

    //private var currMovie : MovieItemDao_Impl =


    private fun showResult(items: Items?) {
        deleteAll()
        items?.results?.forEach { movie ->
            movie.checked = false
            insert(movie)
        }
    }

    private fun insert(item: FoodItem) = scope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    private fun deleteAll() = scope.launch (Dispatchers.IO){
        repository.deleteAll()
    }




}
package com.example.grocerylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieViewModel(application: Application): AndroidViewModel(application){

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob+ Dispatchers.Main


    private val scope = CoroutineScope(coroutineContext)

    private var disposable: Disposable? = null


    private val repository: FoodItemRepository = FoodItemRepository(ItemRoomDatabase.getDatabase(application).itemDao())

    var allMovies: LiveData<List<FoodItem>>

    private lateinit var sortMovies: LiveData<List<FoodItem>>

    init {
        allMovies = repository.allMovies
    }

    //private var currMovie : MovieItemDao_Impl =

    fun getMovie(): MovieItem {
        return movie
    }

    fun setMovie( movie: MovieItem) {
        this.movie = movie
    }

    fun setLike(bol: Boolean) = scope.launch(Dispatchers.IO){
        repository.updateLike(bol, movie.title)
    }

    fun sortByReleaseDate() {
        sortMovies = repository.sortByReleaseDate()
        allMovies = sortMovies
    }

    fun sortByRating() {
        sortMovies = repository.sortByRating()
        allMovies = sortMovies
    }

    fun sortByLiked() {
        sortMovies = repository.sortByLiked()
        allMovies = sortMovies
    }

    fun refreshMovies(page: Int){

        disposable =
            RetrofitService.create("https://api.themoviedb.org/3/").getNowPlaying("4172fe133f29acbe6dda43c60da86a83",page).subscribeOn(
                Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(
                {result -> showResult(result)},
                {error -> showError(error)})
    }

    private fun showResult(movies: Movies?) {
        deleteAll()
        movies?.results?.forEach { movie ->
            movie.liked = false
            insert(movie)
        }
    }

    private fun insert(movie: MovieItem) = scope.launch(Dispatchers.IO) {
        repository.insert(movie)
    }

    private fun deleteAll() = scope.launch (Dispatchers.IO){
        repository.deleteAll()
    }




}
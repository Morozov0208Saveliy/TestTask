package com.example.testtask.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.manager.FavoritesManager
import com.example.data.repository.impl.CourseRepositoryImpl
import com.example.domain.repository.CourseRepository
import com.example.testtask.viewmodel.FavoritesViewModel
import com.example.testtask.viewmodel.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    private var repository: CourseRepository? = null
    private var favoritesManager: FavoritesManager? = null

    fun getRepository(context: Context): CourseRepository {
        return repository ?: CourseRepositoryImpl(
            context,
            getFavoritesManager(context)
        ).also { repository = it }
    }

    private fun getFavoritesManager(context: Context): FavoritesManager {
        return favoritesManager ?: FavoritesManager(context).also { favoritesManager = it }
    }

    fun viewModelFactory(context: Context): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = getRepository(context)
                return when {
                    modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                        MainViewModel(repository) as T
                    }
                    modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                        FavoritesViewModel(repository) as T
                    }
                    else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
        }
    }
}
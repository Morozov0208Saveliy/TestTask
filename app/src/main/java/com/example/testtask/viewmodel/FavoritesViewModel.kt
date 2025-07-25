package com.example.testtask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.Course
import com.example.domain.repository.CourseRepository

class FavoritesViewModel(private val repository: CourseRepository) : ViewModel() {
    private val _favorites = MutableLiveData<List<Course>>()
    val favorites: LiveData<List<Course>> = _favorites

    fun loadFavorites() {
        _favorites.value = repository.getFavorites()
    }
}
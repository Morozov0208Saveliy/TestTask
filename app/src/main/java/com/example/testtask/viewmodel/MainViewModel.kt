package com.example.testtask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Course
import com.example.domain.repository.CourseRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CourseRepository) : ViewModel() {
    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>> = _courses

    fun loadCourses() {
        viewModelScope.launch {
            _courses.value = repository.getCourses()
        }
    }

    fun sortCoursesByDate() {
        val currentList = _courses.value
        if (currentList != null) {
            _courses.value = currentList.sortedByDescending { it.publishDate }
        }
    }

    fun toggleFavorite(courseId: Int) {
        repository.toggleFavorite(courseId)
        loadCourses()
    }
}
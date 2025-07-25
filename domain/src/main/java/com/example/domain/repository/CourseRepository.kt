package com.example.domain.repository

import com.example.domain.model.Course

interface CourseRepository {
    suspend fun getCourses(): List<Course>
    fun getFavorites(): List<Course>
    fun toggleFavorite(courseId: Int)
}
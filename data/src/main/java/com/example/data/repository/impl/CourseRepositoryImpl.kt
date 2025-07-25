package com.example.data.repository.impl

import android.content.Context
import com.example.data.manager.AssetsDataLoader
import com.example.data.manager.FavoritesManager
import com.example.domain.model.Course
import com.example.domain.repository.CourseRepository

class CourseRepositoryImpl(
    private val context: Context,
    private val favoritesManager: FavoritesManager
) : CourseRepository {

    private val dataLoader = AssetsDataLoader(context)

    override suspend fun getCourses(): List<Course> {
        val courses = dataLoader.loadCourses()
        val favoriteIds = favoritesManager.getFavorites()
        return courses.map { course ->
            course.copy(hasLike = course.id in favoriteIds)
        }
    }

    override fun getFavorites(): List<Course> {
        val courses = dataLoader.loadCourses()
        val favoriteIds = favoritesManager.getFavorites()
        return courses.filter { it.id in favoriteIds }
    }

    override fun toggleFavorite(courseId: Int) {
        favoritesManager.toggleFavorite(courseId)
    }
}
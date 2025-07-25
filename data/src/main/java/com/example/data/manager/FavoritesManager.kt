package com.example.data.manager

import android.content.Context
import android.content.SharedPreferences
import com.example.domain.model.Course

class FavoritesManager(context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    fun getFavorites(): Set<Int> {
        return sharedPref.getStringSet("favorite_ids", setOf())?.map { it.toInt() }?.toSet()
            ?: setOf()
    }

    fun toggleFavorite(courseId: Int) {
        val favorites = getFavorites().toMutableSet()
        if (courseId in favorites) {
            favorites.remove(courseId)
        } else {
            favorites.add(courseId)
        }
        sharedPref.edit()
            .putStringSet("favorite_ids", favorites.map { it.toString() }.toSet())
            .apply()
    }
}
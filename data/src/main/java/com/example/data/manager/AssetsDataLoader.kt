package com.example.data.manager

import android.content.Context
import com.example.domain.model.Course
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AssetsDataLoader(private val context: Context) {
    fun loadCourses(): List<Course> {
        return try {
            val jsonString = context.assets.open("courses.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<Map<String, List<Course>>>() {}.type
            val data: Map<String, List<Course>> = Gson().fromJson(jsonString, type)
            data["courses"] ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
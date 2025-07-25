package com.example.testtask.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Course
import com.example.testtask.R

class CourseAdapter(
    private val onBookmarkClick: (Int) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    private var courses: List<Course> = emptyList()

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.course_title)
        val description: TextView = itemView.findViewById(R.id.course_description)
        val price: TextView = itemView.findViewById(R.id.course_price)
        val rating: TextView = itemView.findViewById(R.id.rating_text)
        val date: TextView = itemView.findViewById(R.id.date_text)
        val bookmark: ImageButton = itemView.findViewById(R.id.bookmark_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course_card, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]

        holder.title.text = course.title
        holder.description.text = course.text
        holder.description.maxLines = 2
        holder.description.ellipsize = TextUtils.TruncateAt.END
        holder.price.text = "${course.price} Р"
        holder.rating.text = course.rate
        holder.date.text = course.startDate

        // Update bookmark icon
        val iconRes = if (course.hasLike) R.drawable.bookmark_filled else R.drawable.bookmark
        holder.bookmark.setImageResource(iconRes)

        // Bookmark click handler
        holder.bookmark.setOnClickListener {
            onBookmarkClick(course.id)
        }
    }

    override fun getItemCount() = courses.size

    fun submitList(newCourses: List<Course>) {
        courses = newCourses
        notifyDataSetChanged()
    }
}
package com.example.testtask.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView // Добавил для примера
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.R
import com.example.testtask.adapter.CourseAdapter
import com.example.testtask.databinding.FragmentMainBinding
import com.example.testtask.di.AppModule
import com.example.testtask.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            AppModule.viewModelFactory(requireContext().applicationContext)
        ).get(MainViewModel::class.java)

        setupRecyclerView()
        setupListeners()

        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            adapter.submitList(courses)
        }

        viewModel.loadCourses()
    }

    private fun setupRecyclerView() {
        adapter = CourseAdapter { courseId ->
            viewModel.toggleFavorite(courseId)
        }
        binding.coursesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.coursesRecyclerView.adapter = adapter
    }

    private fun setupListeners() {
        binding.sortByDateText.setOnClickListener {
            viewModel.sortCoursesByDate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
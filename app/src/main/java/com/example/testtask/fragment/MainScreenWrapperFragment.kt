package com.example.testtask.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.testtask.R
import com.example.testtask.databinding.FragmentMainScreenWrapperBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * MainScreenWrapperFragment: Этот фрагмент является контейнером, который отображается после входа.
 * Он содержит BottomNavigationView и NavHostFragment, который переключает между
 * вкладками: Главная (MainFragment), Избранное (FavoritesFragment) и Аккаунт (AccountFragment).
 */
class MainScreenWrapperFragment : Fragment() {

    private var _binding: FragmentMainScreenWrapperBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenWrapperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.bottom_nav_tabs_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
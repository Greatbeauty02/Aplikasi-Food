package com.uasppb.appfood054.Ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager // Import ini

import com.uasppb.appfood054.Data.viewmodel.HomeViewModel // GUNAKAN HomeViewModel
import com.uasppb.appfood054.Data.viewmodel.ViewModelFactory // Import ViewModelFactory
import com.uasppb.appfood054.Ui.adapter.CategoryAdapter
import com.uasppb.appfood054.Ui.adapter.FoodAdapter
import com.uasppb.appfood054.databinding.FragmentCategoryBinding // SESUAIKAN DENGAN NAMA LAYOUT BARU

import kotlinx.coroutines.launch

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireActivity().application)
    }

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter(emptyList()) { category ->

            viewModel.fetchMealsByCategory(category.strCategory)
            Log.d("CategoryFragment", "Category clicked: ${category.strCategory}")
        }
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }


        foodAdapter = FoodAdapter { meal ->

            val action = CategoryFragmentDirections.actionCategoryFragmentToDetailFragment(meal.idMeal)
            findNavController().navigate(action)
            Log.d("CategoryFragment", "Meal clicked: ${meal.strMeal} with ID: ${meal.idMeal}")
        }
        binding.rvFoods.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = foodAdapter
        }


        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.categories.observe(viewLifecycleOwner) { categoriesList ->
                categoryAdapter.updateList(categoriesList)
                Log.d("CategoryFragment", "Categories updated: ${categoriesList.size} items")
            }

            viewModel.meals.collect { mealsList ->
                foodAdapter.submitList(mealsList)
                Log.d("CategoryFragment", "Meals updated: ${mealsList.size} items")

                binding.rvFoods.visibility = if (mealsList.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }

        binding.etSearch.addTextChangedListener { editable ->
            val query = editable?.toString()
            if (!query.isNullOrBlank()) {
                viewModel.searchMeals(query)
                Log.d("CategoryFragment", "Searching for: $query")
            } else {

                viewModel.fetchMealsByCategory("Beef")
                Log.d("CategoryFragment", "Search cleared, reloading default meals.")
            }
        }

        binding.ivSearchIcon.setOnClickListener {
            val query = binding.etSearch.text.toString()
            if (!query.isNullOrBlank()) {
                viewModel.searchMeals(query)
                Log.d("CategoryFragment", "Search icon clicked for: $query")
            }
        }


        viewModel.fetchCategories()
        viewModel.fetchMealsByCategory("Beef")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
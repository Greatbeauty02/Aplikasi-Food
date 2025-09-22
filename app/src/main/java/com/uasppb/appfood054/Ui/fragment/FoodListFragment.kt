package com.uasppb.appfood054.Ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager // Import ini
import com.uasppb.appfood054.Ui.adapter.FoodAdapter
import com.uasppb.appfood054.databinding.FragmentFoodListBinding
import com.uasppb.appfood054.Data.viewmodel.FoodListViewModel
import kotlinx.coroutines.launch

class FoodListFragment : Fragment() {
    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FoodListViewModel by viewModels()
    private val args: FoodListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FoodAdapter { meal ->
            val action = FoodListFragmentDirections.actionFoodListFragmentToDetailFragment(meal.idMeal)
            findNavController().navigate(action)
        }

        binding.rvFood.layoutManager = GridLayoutManager(requireContext(), 2)


        binding.rvFood.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.meals.collect { mealsList ->
                adapter.submitList(mealsList)
            }
        }
        viewModel.fetchMealsByCategory(args.strCategory)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

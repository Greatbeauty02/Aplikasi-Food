package com.uasppb.appfood054.Ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.uasppb.appfood054.databinding.FragmentSearchBinding
import com.uasppb.appfood054.Ui.adapter.FoodAdapter
import com.uasppb.appfood054.Data.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = FoodAdapter { meal ->
            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(meal.idMeal)
            findNavController().navigate(action)
        }

        binding.rvSearch.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSearch.adapter = adapter

        binding.etSearch.addTextChangedListener { text ->
            text?.let {
                if (it.isNotEmpty()) {
                    viewModel.searchMeal(it.toString())
                }
            }
        }

        viewModel.meals.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

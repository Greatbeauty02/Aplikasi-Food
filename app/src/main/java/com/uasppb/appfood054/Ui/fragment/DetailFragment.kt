package com.uasppb.appfood054.Ui.fragment

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.uasppb.appfood054.Data.viewmodel.DetailViewModel
import com.uasppb.appfood054.databinding.FragmentDetailBinding
import com.uasppb.appfood054.Data.viewmodel.ViewModelFactory



class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory(requireActivity().application)
    }
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchMealDetail(args.idMeal)

        viewModel.meal.observe(viewLifecycleOwner) { meal ->
            meal?.let {
                Glide.with(requireContext())
                    .load(it.strMealThumb)
                    .into(binding.ivMeal)

                binding.tvMealName.text = it.strMeal

                binding.tvCategory.text = it.strCategory ?: "-"
                binding.tvArea.text = it.strArea ?: "-"

                val ingredientsList = listOf(
                    it.strIngredient1, it.strIngredient2, it.strIngredient3,
                    it.strIngredient4, it.strIngredient5, it.strIngredient6,
                    it.strIngredient7, it.strIngredient8, it.strIngredient9,
                    it.strIngredient10
                ).filter { bahan -> !bahan.isNullOrBlank() }

                val bulletedIngredients = StringBuilder()
                for (ingredient in ingredientsList) {
                    bulletedIngredients.append("&#8226; ").append(ingredient).append("<br/>")
                }

                val formattedText: Spanned = Html.fromHtml(bulletedIngredients.toString(), Html.FROM_HTML_MODE_COMPACT)
                binding.tvIngredients.text = formattedText


                binding.tvInstructions.text = it.strInstructions ?: "No instructions."
            }
        }

        binding.ivFavorite.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

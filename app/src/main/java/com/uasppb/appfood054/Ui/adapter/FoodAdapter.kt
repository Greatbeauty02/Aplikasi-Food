package com.uasppb.appfood054.Ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uasppb.appfood054.databinding.ItemFoodBinding
import com.uasppb.appfood054.Data.model.Meal

class FoodAdapter(
    private val onClick: (Meal) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private val foods = mutableListOf<Meal>()

    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.tvFoodName.text = meal.strMeal
            Glide.with(binding.root)
                .load(meal.strMealThumb)
                .into(binding.ivFood)

            binding.root.setOnClickListener {
                onClick(meal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foods[position])
    }

    fun submitList(newList: List<Meal>) {
        foods.clear()
        foods.addAll(newList)
        notifyDataSetChanged()
    }
}

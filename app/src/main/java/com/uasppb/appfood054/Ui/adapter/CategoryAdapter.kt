package com.uasppb.appfood054.Ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uasppb.appfood054.databinding.ItemCategoryBinding

class CategoryAdapter(
    private var list: List<com.uasppb.appfood054.Data.model.Category>,
    private val onItemClick: (com.uasppb.appfood054.Data.model.Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    fun updateList(newList: List<com.uasppb.appfood054.Data.model.Category>) {
        this.list = newList
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: com.uasppb.appfood054.Data.model.Category) {
            binding.tvCategory.text = category.strCategory


            Glide.with(binding.root.context)
                .load(category.strCategoryThumb)
                .into(binding.ivCategory)

            binding.root.setOnClickListener {
                onItemClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

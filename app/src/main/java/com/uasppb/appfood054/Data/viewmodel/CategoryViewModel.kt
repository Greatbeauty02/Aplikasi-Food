package com.uasppb.appfood054.Data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uasppb.appfood054.Data.repository.FoodRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import android.util.Log

class CategoryViewModel : ViewModel() {
    private val repository = FoodRepository()

    private val _categories = MutableLiveData<List<com.uasppb.appfood054.Data.model.Category>>()
    val categories: LiveData<List<com.uasppb.appfood054.Data.model.Category>> = _categories

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = repository.getCategories()
                Log.d("CategoryViewModel", "Fetched categories: ${response.categories.size}")
                _categories.value = response.categories
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error fetching categories", e)
                _categories.value = emptyList()
            }
        }
    }
}

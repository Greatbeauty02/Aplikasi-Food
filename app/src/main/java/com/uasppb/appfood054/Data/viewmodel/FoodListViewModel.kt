package com.uasppb.appfood054.Data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uasppb.appfood054.Data.repository.FoodRepository
import com.uasppb.appfood054.Data.model.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodListViewModel : ViewModel() {
    private val repository = FoodRepository()

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals

    fun fetchMealsByCategory(category: String) {
        viewModelScope.launch {
            try {
                val response = repository.getMealsByCategory(category)
                _meals.value = response.meals
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchMeals(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchMeals(query)
                _meals.value = response.meals ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
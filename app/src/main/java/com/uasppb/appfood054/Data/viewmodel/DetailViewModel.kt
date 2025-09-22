package com.uasppb.appfood054.Data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uasppb.appfood054.Data.model.MealDetail // Penting: Import MealDetail, bukan Meal
import com.uasppb.appfood054.Data.repository.FoodRepository // Pastikan FoodRepository di-import
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: FoodRepository) : ViewModel() {

    private val _meal = MutableLiveData<MealDetail?>()
    val meal: LiveData<MealDetail?> = _meal

    fun fetchMealDetail(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.getMealDetail(id)
                val fetchedMeal = response.meals.firstOrNull()
                _meal.value = fetchedMeal
            } catch (e: Exception) {
                e.printStackTrace()
                _meal.value = null
            }
        }
    }
}
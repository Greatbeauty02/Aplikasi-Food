package com.uasppb.appfood054.Data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uasppb.appfood054.Data.model.Meal
import com.uasppb.appfood054.Data.repository.FoodRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: FoodRepository) : ViewModel() {

    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> get() = _meals

    fun searchMeal(keyword: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchMeals(keyword)
                _meals.value = response.meals ?: emptyList()
            } catch (e: Exception) {
                _meals.value = emptyList()
            }
        }
    }
}

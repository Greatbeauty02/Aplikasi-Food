package com.uasppb.appfood054.Data.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uasppb.appfood054.Data.model.Meal
import com.uasppb.appfood054.Data.repository.FoodRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(private val repository: FoodRepository) : ViewModel() {


    private val _categories = MutableLiveData<List<com.uasppb.appfood054.Data.model.Category>>()
    val categories: LiveData<List<com.uasppb.appfood054.Data.model.Category>> = _categories


    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals

    private var searchJob: Job? = null

    init {

        fetchCategories()

        fetchMealsByCategory("Beef")
    }

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = repository.getCategories()
                _categories.value = response.categories
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching categories", e)
                _categories.value = emptyList()
            }
        }
    }

    fun fetchMealsByCategory(category: String) {
        viewModelScope.launch {
            try {
                val response = repository.getMealsByCategory(category)
                _meals.value = response.meals ?: emptyList()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching meals by category $category", e)
                _meals.value = emptyList()
            }
        }
    }


    fun searchMeals(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            if (query.isEmpty()) {

                fetchMealsByCategory("Beef")
                return@launch
            }
            try {
                val response = repository.searchMeals(query)
                _meals.value = response.meals ?: emptyList()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error searching meals for $query", e)
                _meals.value = emptyList()
            }
        }
    }
}

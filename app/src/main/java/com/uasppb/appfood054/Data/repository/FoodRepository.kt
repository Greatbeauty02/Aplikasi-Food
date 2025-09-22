package com.uasppb.appfood054.Data.repository

import com.uasppb.appfood054.Data.Connection.RetrofitClient

class FoodRepository {
    private val api = com.uasppb.appfood054.Data.Connection.RetrofitClient.apiService

    suspend fun getCategories() = api.getCategories()

    suspend fun getMealsByCategory(category: String) = api.getMealsByCategory(category)

    suspend fun searchMeals(query: String) = api.searchMeals(query)

    suspend fun getMealDetail(id: String) = api.getMealDetail(id)


}
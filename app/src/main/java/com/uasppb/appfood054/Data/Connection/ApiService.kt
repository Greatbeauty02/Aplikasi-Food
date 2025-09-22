package com.uasppb.appfood054.Data.Connection


import com.uasppb.appfood054.Data.model.CategoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String
    ): com.uasppb.appfood054.Data.model.MealsResponse

    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") keyword: String
    ): com.uasppb.appfood054.Data.model.MealsResponse

    @GET("lookup.php")
    suspend fun getMealDetail(
        @Query("i") mealId: String
    ): com.uasppb.appfood054.Data.model.MealDetailResponse
}

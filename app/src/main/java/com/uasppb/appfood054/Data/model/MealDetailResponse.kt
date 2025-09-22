package com.uasppb.appfood054.Data.model

import com.google.gson.annotations.SerializedName

data class MealDetailResponse(
    @SerializedName("meals")
    val meals: List<com.uasppb.appfood054.Data.model.MealDetail>
)
package com.uasppb.appfood054.Data.model

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("categories")
    val categories: List<com.uasppb.appfood054.Data.model.Category>
)

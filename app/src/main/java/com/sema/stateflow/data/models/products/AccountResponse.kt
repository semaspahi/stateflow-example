package com.sema.stateflow.data.models.products

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Product")
    val product: ProductDetailsResponse,
    @SerializedName("PlanValue")
    val planValue: Float
)
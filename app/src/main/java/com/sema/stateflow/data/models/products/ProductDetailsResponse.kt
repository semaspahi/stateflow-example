package com.sema.stateflow.data.models.products

import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName("FriendlyName")
    val friendlyName: String
)
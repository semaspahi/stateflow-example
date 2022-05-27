package com.sema.stateflow.data.api

import com.google.gson.annotations.SerializedName

data class HttpErrorData(
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Message")
    val message: String?,
)

package com.sema.stateflow.data.models.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("Session")
    val session: SessionDataResponse,
    @SerializedName("User")
    val user: UserResponse
)
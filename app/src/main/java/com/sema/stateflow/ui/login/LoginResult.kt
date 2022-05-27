package com.sema.stateflow.ui.login

import com.sema.stateflow.data.models.login.LoginResponse

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoginResponse? = null,
    val error: String? = null
)
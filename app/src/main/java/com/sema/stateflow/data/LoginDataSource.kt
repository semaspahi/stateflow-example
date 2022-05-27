package com.sema.stateflow.data

import com.sema.stateflow.data.api.ApiService
import com.sema.stateflow.data.models.login.LoginRequest
import com.sema.stateflow.data.models.login.LoginResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import com.sema.stateflow.data.api.Result
import com.sema.stateflow.data.api.BaseApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

@ActivityRetainedScoped
class LoginDataSource @Inject constructor(
    private val apiService: ApiService,
    private val scope: CoroutineScope
) : BaseApiResponse() {

    suspend fun login(username: String, password: String): Flow<Result<LoginResponse?>> {
        return flow {
            val result = safeApiCall { apiService.login(LoginRequest(email = username, password = password))}
            emit(result)
        }.shareIn(scope, SharingStarted.WhileSubscribed())
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
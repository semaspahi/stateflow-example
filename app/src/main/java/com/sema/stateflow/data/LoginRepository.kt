package com.sema.stateflow.data

import com.sema.stateflow.data.models.login.LoginResponse
import com.sema.stateflow.data.api.Result
import com.sema.stateflow.data.networking.HeaderInterceptor
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

@ActivityRetainedScoped
class LoginRepository @Inject constructor(
    private val dataSource: LoginDataSource,
    private val interceptor: HeaderInterceptor
) {

    // in-memory cache of the loggedInUser object
    var user: LoginResponse? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        interceptor.setSessionToken(null)
        dataSource.logout()
    }

    suspend fun login(username: String, password: String): Flow<Result<LoginResponse?>> {
        // handle login
        return dataSource.login(username, password)
    }

     fun setLoggedInUser(loggedInUser: LoginResponse?) {
        this.user = loggedInUser
        interceptor.setSessionToken(user?.session?.bearerToken) // todo: save token on local data
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

}
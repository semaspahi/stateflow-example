package com.sema.stateflow.ui.login

import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.sema.stateflow.data.LoginRepository
import com.sema.stateflow.R
import com.sema.stateflow.data.api.Result
import com.sema.stateflow.data.models.login.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginForm = MutableStateFlow(LoginFormState())
    val loginFormState: StateFlow<LoginFormState> = _loginForm

    private val _loginResult = MutableStateFlow(LoginResult())
    val loginResult: StateFlow<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(username, password)
                .collect(::handleResult)
        }
    }

    private fun handleResult(result: Result<LoginResponse?>) {
        if (result is Result.Success) {
            loginRepository.setLoggedInUser(result.data)
            _loginResult.value = LoginResult(success = result.data as LoginResponse)
        } else if (result is Result.Error) _loginResult.value =
            LoginResult(error = result.exception?.name)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
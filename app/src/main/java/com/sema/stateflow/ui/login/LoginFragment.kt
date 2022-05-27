package com.sema.stateflow.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sema.stateflow.databinding.FragmentLoginBinding
import com.sema.stateflow.ui.BaseFragment
import com.sema.stateflow.util.afterTextChanged
import com.sema.stateflow.util.editorChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initializeView()
        binding.initializeObservers()
    }

    @SuppressLint("SetTextI18n")
    private fun FragmentLoginBinding.initializeView() {
        username.afterTextChanged { loginDataChanged() }
        password.afterTextChanged { loginDataChanged() }
        password.editorChangedListener { loginDataChanged() }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            loginViewModel.login(
                username.text.toString(),
                password.text.toString()
            )
        }
    }

    private fun FragmentLoginBinding.loginDataChanged() {
        loginViewModel.loginDataChanged(
            username.text.toString(),
            password.text.toString()
        )
    }

    private fun FragmentLoginBinding.initializeObservers() {
        lifecycleScope.launchWhenStarted {
            launch {
                loginViewModel.loginResult.collect { loginResult ->
                    loading.visibility = View.GONE
                    loginResult.error?.let { showLoginFailed(it) }
                    loginResult.success?.let { navigateAfterAfterLoginSuccess() }
                }
            }
            launch {
                loginViewModel.loginFormState.collect { loginFormState ->
                    login.isEnabled = loginFormState.isDataValid
                    loginFormState.usernameError?.let { username.error = getString(it) }
                    loginFormState.passwordError?.let { password.error = getString(it) }
                }
            }
        }
    }

    private fun navigateAfterAfterLoginSuccess() {
        val action = LoginFragmentDirections.actionLoginToPlanDetail()
        findNavController().navigate(action)
    }

    private fun showLoginFailed(errorString: String) = showToast(errorString)
}
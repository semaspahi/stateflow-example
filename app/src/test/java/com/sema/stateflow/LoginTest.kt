package com.sema.stateflow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sema.stateflow.data.LoginDataSource
import com.sema.stateflow.data.LoginRepository
import com.sema.stateflow.data.api.ApiService
import com.sema.stateflow.data.networking.HeaderInterceptor
import com.sema.stateflow.ui.login.LoginViewModel
import com.sema.stateflow.util.CoroutineScope
import com.sema.stateflow.util.MainCoroutineRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Unit tests for the [LoginViewModel].
 */
@ExperimentalCoroutinesApi
class LoginTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var dataSource: LoginDataSource

    @Mock
    lateinit var interceptor: HeaderInterceptor

    @Mock
    lateinit var repository: LoginRepository

    @Mock
    lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = LoginDataSource(apiService, coroutineScope)
        repository = LoginRepository(dataSource, interceptor)
        viewModel = LoginViewModel(repository)
    }

    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Overrides Dispatchers.Main used in Coroutines
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val testDispatcher = coroutineRule.testDispatcher

    private val coroutineScope = coroutineRule.CoroutineScope()

    @Test
    fun testExampleWithCorrectValues() = runTest {
        val validEmail = "androidtest@sema.com"
        val validPassword = "P455word12"

        viewModel.login(validEmail, validPassword)
        assertEquals(true, viewModel.loginResult.value.success != null)
    }

    @Test
    fun testExampleWithIncorrectEmail() = runTest {
        val invalidEmail = "email1"
        val validPassword = "P455word12"

        viewModel.login(invalidEmail, validPassword)
        assertEquals(false, viewModel.loginResult.value.success != null)
    }


    @Test
    fun testExampleWithIncorrectPassword() = runTest {
        val validEmail = "androidtest@sema.com"
        val invalidPassword = "password1"

        viewModel.login(validEmail, invalidPassword)
        assertEquals(false, viewModel.loginResult.value.success != null)
    }

    @Test
    fun testExampleWithIncorrectValues() = runTest {
        val invalidEmail = "email1"
        val invalidPassword = "password1"

        viewModel.login(invalidEmail, invalidPassword)
        assertEquals(false, viewModel.loginResult.value.success != null)
    }
}
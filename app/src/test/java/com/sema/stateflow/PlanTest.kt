package com.sema.stateflow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sema.stateflow.data.PlanDataSource
import com.sema.stateflow.data.PlanRepository
import com.sema.stateflow.data.api.ApiService
import com.sema.stateflow.ui.plan.PlanViewModel
import com.sema.stateflow.util.CoroutineScope
import com.sema.stateflow.util.MainCoroutineRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Unit tests for the [PlanViewModel].
 */
@ExperimentalCoroutinesApi
class PlanTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var dataSource: PlanDataSource

    @Mock
    lateinit var repository: PlanRepository

    @Mock
    lateinit var viewModel: PlanViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = PlanDataSource(apiService, coroutineScope)
        repository = PlanRepository(dataSource)
        viewModel = PlanViewModel(repository)
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
    fun testExampleWithProductResultSuccess() = runBlocking {
        viewModel.getInvestorProducts()
        assertEquals(true, viewModel.investorProductResult.value.success != null)
    }

}
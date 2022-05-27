package com.sema.stateflow.ui.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sema.stateflow.data.PlanRepository
import com.sema.stateflow.data.api.Result
import com.sema.stateflow.data.models.products.AllProductsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlanViewModel @Inject constructor(
    private val planRepository: PlanRepository
) : ViewModel() {

    private val _investorProductResult = MutableStateFlow(InvestorProductResult())
    val investorProductResult: StateFlow<InvestorProductResult> = _investorProductResult

    init {
        getInvestorProducts()
    }

    fun getInvestorProducts() {
        viewModelScope.launch {
            planRepository.getInvestorProducts()
                .collect(::handleResult)
        }
    }

    private fun handleResult(result: Result<AllProductsResponse?>) {
        when (result) {
            is Result.Success -> _investorProductResult.value =
                InvestorProductResult(success = result.data, loading = false)
            is Result.Error -> _investorProductResult.value =
                InvestorProductResult(error = result.exception?.message, loading = false)
            is Result.Loading -> _investorProductResult.value =
                InvestorProductResult(loading = true)
        }
    }

}

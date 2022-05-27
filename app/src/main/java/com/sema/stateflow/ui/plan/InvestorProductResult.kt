package com.sema.stateflow.ui.plan

import com.sema.stateflow.data.models.products.AllProductsResponse

data class InvestorProductResult(
    val success: AllProductsResponse? = null,
    val error: String? = null,
    val loading: Boolean = false
)
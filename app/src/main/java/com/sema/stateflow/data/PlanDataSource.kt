package com.sema.stateflow.data

import com.sema.stateflow.data.api.ApiService
import com.sema.stateflow.data.models.products.AllProductsResponse
import com.sema.stateflow.data.api.Result
import com.sema.stateflow.data.api.BaseApiResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Class that retrieves investor products information.
 */

@ActivityRetainedScoped
class PlanDataSource @Inject constructor(
    private val apiService: ApiService,
    private val scope: CoroutineScope
) : BaseApiResponse() {

    suspend fun getInvestorProducts(): Flow<Result<AllProductsResponse?>> {
        return flow {
            emit(Result.Loading)
            val result = safeApiCall { apiService.getInvestorProducts() }
            emit(result)
        }.shareIn(scope, SharingStarted.WhileSubscribed())
    }
}
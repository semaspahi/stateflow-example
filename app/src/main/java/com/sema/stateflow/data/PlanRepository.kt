package com.sema.stateflow.data

import com.sema.stateflow.data.models.products.AllProductsResponse
import com.sema.stateflow.data.api.Result
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Class that requests investor products information from the remote data source
 */

@ActivityRetainedScoped
class PlanRepository @Inject constructor(
    private val dataSource: PlanDataSource,
) {

    suspend fun getInvestorProducts(): Flow<Result<AllProductsResponse?>> {
        return dataSource.getInvestorProducts()
    }

}
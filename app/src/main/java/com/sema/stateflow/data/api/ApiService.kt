package com.sema.stateflow.data.api

import com.sema.stateflow.data.models.login.LoginRequest
import com.sema.stateflow.data.models.login.LoginResponse
import com.sema.stateflow.data.models.products.AllProductsResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("/users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/investorproducts")
    suspend fun getInvestorProducts(): Response<AllProductsResponse>
}

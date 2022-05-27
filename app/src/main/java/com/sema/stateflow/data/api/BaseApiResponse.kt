package com.sema.stateflow.data.api

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let { return Result.Success(it) }
        } else {
            response.errorBody()?.let { return Result.Error(convertErrorBody(it)) }
        }
        return Result.Error()
    }

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> Response<T>
    ): Result<T> {
        return withContext(dispatcher) {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { Result.Success(it) }
            } else {
                response.errorBody()?.let { Result.Error(convertErrorBody(it)) }
            }
            Result.Error()
        }
    }


    private fun convertErrorBody(errorBody: ResponseBody): HttpErrorData? {
        return try {
            val gson = Gson()
            val errorData = gson.fromJson(errorBody.string(), HttpErrorData::class.java)
            errorData
        } catch (exception: Exception) {
            null
        }
    }
}
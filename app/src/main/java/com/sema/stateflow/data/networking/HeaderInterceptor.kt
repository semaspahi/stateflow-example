package com.sema.stateflow.data.networking

import com.sema.stateflow.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    private var token: String? = null

    fun setSessionToken(token: String?) {
        this.token = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader(AUTH_HEADER_NAME, "$AUTH_HEADER_VALUE $token")
                .addHeader(APP_ID_HEADER_NAME, BuildConfig.APP_ID)
                .addHeader(API_VERSION_HEADER_NAME, BuildConfig.API_VERSION)
                .addHeader(APP_VERSION_HEADER_NAME, BuildConfig.VERSION_NAME)
                .addHeader(CONTENT_HEADER_NAME, CONTENT_HEADER_VALUE)
                .build()
        )
    }

    companion object {
        private const val APP_ID_HEADER_NAME = "AppId"
        private const val API_VERSION_HEADER_NAME = "ApiVersion"
        private const val APP_VERSION_HEADER_NAME = "AppVersion"
        private const val CONTENT_HEADER_NAME = "ContentDetail-Type"
        private const val CONTENT_HEADER_VALUE = "application/json"
        private const val AUTH_HEADER_NAME = "Authorization"
        private const val AUTH_HEADER_VALUE = "Bearer"
    }
}
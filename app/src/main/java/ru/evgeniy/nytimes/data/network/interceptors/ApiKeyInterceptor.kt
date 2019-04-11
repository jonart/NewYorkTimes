package ru.evgeniy.nytimes.data.network.interceptors

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response
import ru.evgeniy.nytimes.BuildConfig

class ApiKeyInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val url = request.url().newBuilder()
                .addQueryParameter("api-key", BuildConfig.API_KEY)
                .build()

        request = request.newBuilder()
                .url(url)
                .build()
        return chain.proceed(request)
    }
}

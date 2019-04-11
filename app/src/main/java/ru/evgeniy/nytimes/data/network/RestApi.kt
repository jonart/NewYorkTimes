package ru.evgeniy.nytimes.data.network

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.evgeniy.nytimes.BuildConfig
import ru.evgeniy.nytimes.data.network.interceptors.ApiKeyInterceptor

class RestApi private constructor() {
    val api: Api


    init {
        val okHttpClient = buildOkHttpClient()
        val retrofit = buildRetrofit(okHttpClient)
        api = retrofit.create(Api::class.java)
    }

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(ApiKeyInterceptor())
                .build()
    }


    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {

        private var restApi: RestApi? = null


        val instance: RestApi
            @Synchronized get() {
                if (restApi == null) {
                    restApi = RestApi()
                }
                return restApi as RestApi
            }
    }
}

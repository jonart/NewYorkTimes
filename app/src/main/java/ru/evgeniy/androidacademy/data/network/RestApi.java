package ru.evgeniy.androidacademy.data.network;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.evgeniy.androidacademy.BuildConfig;
import ru.evgeniy.androidacademy.data.network.Interceptors.ApiKeyInterceptor;

public class RestApi {

    private static RestApi restApi;
    private final Api mApi;


    public static synchronized RestApi getInstance() {
        if (restApi == null){
            restApi = new RestApi();
        }
        return restApi;
    }

    private RestApi(){
        final OkHttpClient okHttpClient = buildOkHttpClient();
        final Retrofit retrofit = buildRetrofit(okHttpClient);
        mApi = retrofit.create(Api.class);
    }

    private OkHttpClient buildOkHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new ApiKeyInterceptor())
                .build();
    }

    private Retrofit buildRetrofit(@NonNull OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    public Api getApi(){
        return mApi;
    }
}

package ru.evgeniy.nytimes.data.network.Interceptors;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.evgeniy.nytimes.BuildConfig;

public final class ApiKeyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("api-key", BuildConfig.API_KEY)
                .build();

        request = request.newBuilder()
                .url(url)
                .build();
        return chain.proceed(request);
    }
}

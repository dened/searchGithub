package com.dchuvasov.searchgithub.api.core;

import android.support.annotation.NonNull;

import com.dchuvasov.searchgithub.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by denis_chuvasov on 19.12.16.
 */

public class ServiceCreatorUtil {

    private static OkHttpClient okHttpClient;

    private ServiceCreatorUtil() {
        throw new IllegalAccessError("Utility class");
    }


    public static <S> S createService(Class<S> serviceClass) {
        return buildRetrofit().create(serviceClass);
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor.create())
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = okHttpClient;
        if (client == null) {
            synchronized (ServiceCreatorUtil.class) {
                client = okHttpClient;
                if (client == null) {
                    client = okHttpClient = buildClient();
                }
            }
        }
        return client;
    }

}

package com.dchuvasov.searchgithub.api.core;

import android.support.annotation.NonNull;

import com.dchuvasov.searchgithub.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by denis_chuvasov on 19.12.16.
 */
public class LoggingInterceptor implements Interceptor {
    private final Interceptor loggingInterceptor;

    private LoggingInterceptor() {
        loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return loggingInterceptor.intercept(chain);
    }

    @NonNull
    public static Interceptor create() {
        return new LoggingInterceptor();
    }
}

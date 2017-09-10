package com.example.stream.core.network.rx;

import com.example.stream.core.util.storage.StreamPreference;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by StReaM on 8/30/2017.
 */

public class AddCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(@android.support.annotation.NonNull Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        Observable
                .just(StreamPreference.getCustomAppProfile("Cookie"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String cookie) throws Exception {
                        builder.addHeader("Cookie", cookie);
                    }
                });

        return chain.proceed(builder.build());
    }
}

package com.fnhelper.photo.interfaces;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 封装公共参数（Key和密码）
 * <p>
 */
public class CommonInterceptor implements Interceptor {

    public CommonInterceptor() {
    }
    HttpUrl.Builder authorizedUrlBuilder;
    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request oldRequest = chain.request();

        if (!TextUtils.isEmpty(Constants.ID) && !TextUtils.isEmpty(Constants.sToken)){
            // 添加新的参数
        authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host())
                    .addEncodedQueryParameter("ID",Constants.ID)
                    .addEncodedQueryParameter("sToken",Constants.sToken);
        }else {
            authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host());
        }

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}
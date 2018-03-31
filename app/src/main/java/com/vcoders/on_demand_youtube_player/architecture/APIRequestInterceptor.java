package com.vcoders.on_demand_youtube_player.architecture;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class APIRequestInterceptor implements Interceptor {

    private Context mContext;
    private boolean isHeader = true;
    private boolean isAuthorization = false;

    public APIRequestInterceptor(boolean isHeader){
        this.isHeader = isHeader;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        final String basic = "Basic " + "TXlwYXk6TXlwYXk=";

        Request.Builder requestBuilder = original.newBuilder()
                .method(original.method(), original.body());

        if(isHeader){
            requestBuilder.header("Authorization", ServicesConfig.getInstance().getAUTHORIZATION())
                    .addHeader("Content-Type", "application/x-www-form-urlencoded");
        }

        Request request = requestBuilder.build();

        return chain.proceed(request);
    }

    public void setAuthorization(boolean authorization) {
        isAuthorization = authorization;
    }
}
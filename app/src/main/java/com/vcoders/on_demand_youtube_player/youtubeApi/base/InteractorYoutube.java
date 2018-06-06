package com.vcoders.on_demand_youtube_player.youtubeApi.base;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.BaseResponse;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.services.APIRequestInterceptor;
import com.vcoders.on_demand_youtube_player.youtubeApi.services.YoutubeServiceAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class InteractorYoutube<ResultType> {

    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected YoutubeServiceAPI youtubeServiceAPI;
    protected Map<String, Object> body = new HashMap<>();
    private boolean isHeader = false;
    public Context context;
    private RequestAPIListener requestAPIListener;
    private ResponseAPIListener responseAPIListener = new ResponseAPIListener();
    private String BASE_URL = Constant.BASE_URL_YOUTUBE;

    public InteractorYoutube(Context context) {
        this.context = context;
    }

    protected YoutubeServiceAPI getYoutubeService() {
        return youtubeServiceAPI;
    }

    protected abstract Observable<Response<ResultType>> buildObservable();

    protected void setBaseURL(String url) {
        BASE_URL = url;
    }

    protected void run() {
        setUpRequest();
        compositeDisposable.add(buildObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ResultType>>() {
                    @Override
                    public void accept(@NonNull Response<ResultType> resultType) throws Exception {
                        postResponse(resultType);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        postError(throwable);
                    }
                }));
    }

    private void postResponse(Response<ResultType> resultType) {
        if (resultType instanceof Response) {
            postSuccess(resultType.body());
            return;
        }

        BaseResponse baseResponse = (BaseResponse) resultType.body();

        if (baseResponse.getResponseCode() != 0) {
            String message = baseResponse.getResponseMessage();
            int code = baseResponse.getResponseCode();
            postError(code, message);
            return;
        }

        postSuccess(resultType.body());
    }

    private void postError(Throwable throwable) {
        if (requestAPIListener != null) {
            responseAPIListener.setErrorCode(0);
            responseAPIListener.setErrorMessage(throwable.getMessage());
            requestAPIListener.onResponse(responseAPIListener);
        }
    }

    private void postError(int errorCode, String errorMessage) {
        if (requestAPIListener != null) {
            responseAPIListener.setErrorCode(errorCode);
            responseAPIListener.setErrorMessage(errorMessage);
            requestAPIListener.onResponse(responseAPIListener);
        }
    }

    private void postSuccess(ResultType resultType) {
        if (requestAPIListener != null) {
            responseAPIListener.setData(resultType);
            requestAPIListener.onResponse(responseAPIListener);
        }
    }

    public void unSubscribe() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }

    protected void setRequestAPIListener(RequestAPIListener listener) {
        this.requestAPIListener = listener;
    }

    private void setUpRequest() {

        try {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.addInterceptor(new APIRequestInterceptor(isHeader))
                    .addInterceptor(logging)
                    .readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
            youtubeServiceAPI = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(YoutubeServiceAPI.class);

        } catch (Exception e) {

        }
    }

    protected RequestBody createPartFromString(String str) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), str);
    }

    public void setHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }
}

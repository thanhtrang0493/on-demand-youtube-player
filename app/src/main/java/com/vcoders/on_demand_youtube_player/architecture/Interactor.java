package com.vcoders.on_demand_youtube_player.architecture;


import android.content.Context;

import com.vcoders.on_demand_youtube_player.services.ServicesAPI;

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
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class Interactor<ResultType> {
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected ServicesAPI servicesAPI;
    protected Map<String, Object> body = new HashMap<>();
    private boolean isHeader = true;
    public Context context;
    private RequestAPIListener listener;

    public Interactor(Context context) {
        this.context = context;
    }

    protected ServicesAPI getServicesAPI() {
        return servicesAPI;
    }

    protected abstract Observable<ResultType> buildObservable();

    protected abstract void response(ResultType resultType);

    protected abstract void error(int errorCode, String errorMessage);

    private void postResponse(ResultType resultType) {
        response(resultType);
    }

    private void postError(Throwable throwable) {
        error(0, throwable.getMessage());
    }

    protected void run() {
        setUpRequest();
        compositeDisposable.add(buildObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultType>() {
                    @Override
                    public void accept(@NonNull ResultType resultType) throws Exception {
                        postResponse(resultType);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        postError(throwable);
                    }
                }));
    }

    protected void setServicesListener(RequestAPIListener listener) {
        this.listener = listener;
    }

    public void unSubscribe() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
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
            servicesAPI = new Retrofit.Builder()
                    .baseUrl(ServicesConfig.getInstance().getDOMAIN())
                    .client(okHttpClientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ServicesAPI.class);

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

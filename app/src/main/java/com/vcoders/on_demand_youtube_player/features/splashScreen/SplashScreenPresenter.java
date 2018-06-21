package com.vcoders.on_demand_youtube_player.features.splashScreen;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.database.DatabaseResponseListener;
import com.vcoders.on_demand_youtube_player.database.tables.topic.TopicLoader;
import com.vcoders.on_demand_youtube_player.model.Topic;
import com.vcoders.on_demand_youtube_player.utils.AccountUtils;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import java.util.List;

import javax.inject.Inject;


public class SplashScreenPresenter extends BasePresenter<SplashScreenView, SplashScreenRouter>
        implements DatabaseResponseListener<List<Topic>> {

    Context context;
    int DELAY_SPLASH_SCREEN = 1000;
    TopicLoader topicLoader;

    @Inject
    public SplashScreenPresenter(Context context) {
        this.context = context;
        topicLoader = new TopicLoader(context);
    }

    public void showSplashScreen() {
        Utils.getInstance().retrieveSharedPreferences(context);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getListTopic();
            }
        }, DELAY_SPLASH_SCREEN);
    }

    private void getListTopic() {
        topicLoader.getListTopicLoader(this);
    }

    @Override
    public void onDatabaseResponse(List<Topic> response) {
        if (response != null) {
            if (AccountUtils.ourInstance.isLogin() || response.size() > 0)
                getRouter().toHome();
            else
                getRouter().toOnBoarding();
        } else
            getRouter().toOnBoarding();
        ((Activity) context).finish();
    }

    @Override
    public void onDatabaseError() {

    }
}

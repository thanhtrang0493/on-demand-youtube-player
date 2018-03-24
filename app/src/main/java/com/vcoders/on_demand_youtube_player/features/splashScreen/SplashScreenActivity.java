package com.vcoders.on_demand_youtube_player.features.splashScreen;

import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.ApplicationModule;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;

import javax.inject.Inject;

public class SplashScreenActivity extends BaseActivity {

    SplashScreenComponent splashScreenComponent;

    @Inject
    SplashScreenPresenter splashScreenPresenter;

    @Inject
    SplashScreenRouter splashScreenRouter;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        splashScreenPresenter.showSplashScreen();
    }

    @Override
    protected int getViewResource() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected String getTitleActionBar() {
        return null;
    }

    @Override
    protected TypeActionBar[] getTypeActionBar() {
        return new TypeActionBar[0];
    }

    @Override
    protected BaseRouter getRouter() {
        return splashScreenRouter;
    }

    @Override
    protected BasePresenter getPresenter() {
        return splashScreenPresenter;
    }

    @Override
    protected void inject() {
        splashScreenComponent = DaggerSplashScreenComponent.builder().
                applicationModule(new ApplicationModule(this)).build();
        splashScreenComponent.inject(this);
    }

    @Override
    protected BaseComponent getActivityComponent() {
        return splashScreenComponent;
    }
}

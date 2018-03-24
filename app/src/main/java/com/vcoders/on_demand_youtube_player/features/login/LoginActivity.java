package com.vcoders.on_demand_youtube_player.features.login;

import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.ApplicationModule;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity {

    LoginComponent loginComponent;

    @Inject
    LoginPresenter loginPresenter;

    @Inject
    LoginRouter loginRouter;

    @Override
    protected void initializeView(Bundle savedInstanceState) {

    }

    @Override
    protected int getViewResource() {
        return R.layout.activity_login;
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
        return loginRouter;
    }

    @Override
    protected BasePresenter getPresenter() {
        return loginPresenter;
    }

    @Override
    protected void inject() {
        loginComponent = DaggerLoginComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        loginComponent.inject(this);
    }

    @Override
    protected BaseComponent getActivityComponent() {
        return loginComponent;
    }
}

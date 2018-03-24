package com.vcoders.on_demand_youtube_player.features.passwordRecovery;


import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.ApplicationModule;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;

import javax.inject.Inject;

public class PasswordRecoveryActivity extends BaseActivity {

    PasswordRecoveryComponent passwordRecoveryComponent;

    @Inject
    PasswordRecoveryPresenter passwordRecoveryPresenter;

    @Inject
    PasswordRecoveryRouter passwordRecoveryRouter;

    @Override
    protected void initializeView(Bundle savedInstanceState) {

    }

    @Override
    protected int getViewResource() {
        return R.layout.activity_password_recovery;
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
        return passwordRecoveryRouter;
    }

    @Override
    protected BasePresenter getPresenter() {
        return passwordRecoveryPresenter;
    }

    @Override
    protected void inject() {
        passwordRecoveryComponent = DaggerPasswordRecoveryComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        passwordRecoveryComponent.inject(this);
    }

    @Override
    protected BaseComponent getActivityComponent() {
        return passwordRecoveryComponent;
    }
}

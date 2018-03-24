package com.vcoders.on_demand_youtube_player.features.signup;

import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.ApplicationModule;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;

import javax.inject.Inject;

public class SignUpActivity extends BaseActivity {

    SignUpComponent signUpComponent;

    @Inject
    SignUpPresenter signUpPresenter;

    @Inject
    SignUpRouter signUpRouter;

    @Override
    protected void initializeView(Bundle savedInstanceState) {

    }

    @Override
    protected int getViewResource() {
        return R.layout.activity_sign_up;
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
        return signUpRouter;
    }

    @Override
    protected BasePresenter getPresenter() {
        return signUpPresenter;
    }

    @Override
    protected void inject() {
        signUpComponent = DaggerSignUpComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        signUpComponent.inject(this);
    }

    @Override
    protected BaseComponent getActivityComponent() {
        return signUpComponent;
    }
}

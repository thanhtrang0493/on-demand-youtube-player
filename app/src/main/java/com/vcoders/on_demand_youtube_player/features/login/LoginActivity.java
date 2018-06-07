package com.vcoders.on_demand_youtube_player.features.login;

import android.content.Intent;
import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.ApplicationModule;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import javax.inject.Inject;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

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

    boolean isLogin = true;

    @OnClick(R.id.llLogin)
    public void onLLLoginClick() {
        if (isLogin) {
            loginPresenter.login();
            isLogin = false;
        } else {
            loginPresenter.logout();
            isLogin = true;
        }
    }

    @Override
    public void showError(String error) {
        Utils.getInstance().showError(this, error);
    }

    @Override
    public void showLoading(boolean isShow) {
        Utils.getInstance().showLoading(this, isShow);
    }

    @Override
    public void onLoginSuccess() {
        finish();
    }

    @Override
    public void onLoginFail(int errorCode, String errorMessage) {
        showError(errorMessage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            loginPresenter.notifyActivityResponse(data, loginPresenter.app.RC_FAIL);
        } else {
            loginPresenter.notifyActivityResponse(data, loginPresenter.app.RC_AUTH);
        }
    }
}

package com.vcoders.on_demand_youtube_player.architecture;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewResource());
        ButterKnife.bind(this);

        if (getPresenter() != null) {
            getPresenter().setView(this);
            getPresenter().setRouter(getRouter());
        }

        inject();

        setupActionbar();

        initializeView(savedInstanceState);
    }

    private void setupActionbar(){

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getPresenter() != null) {
            getPresenter().setView(this);
            getPresenter().setRouter(getRouter());
        }

        try {
            EventBus.getDefault().register(getPresenter());
        } catch (Exception e) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        try {
            EventBus.getDefault().unregister(getPresenter());
        } catch (Exception e) {

        }
    }

    protected abstract void initializeView(Bundle savedInstanceState);

    protected abstract int getViewResource();

    protected abstract String getTitleActionBar();

    protected abstract TypeActionBar[] getTypeActionBar();

    protected abstract BaseRouter getRouter();

    protected abstract BasePresenter getPresenter();

    protected abstract void inject();

    protected abstract BaseComponent getActivityComponent();
}

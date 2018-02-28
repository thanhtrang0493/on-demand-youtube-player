package com.vcoders.on_demand_youtube_player.architecture;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setViewResource());
        ButterKnife.bind(this);

        if (getPresenter() != null) {
            getPresenter().setView(this);
            getPresenter().setRouter(getRouter());
        }

        initializeView();
    }

    protected abstract void initializeView();

    protected abstract int setViewResource();

    protected abstract BasePresenter getPresenter();

    protected abstract BaseRouter getRouter();
}


package com.vcoders.on_demand_youtube_player.features.setTimer;

import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.ApplicationModule;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;

import javax.inject.Inject;

public class SetTimerActivity extends BaseActivity {

    SetTimerComponent setTimerComponent;

    @Inject
    SetTimerPresenter setTimerPresenter;

    @Inject
    SetTimerRouter setTimerRouter;

    @Override
    protected void initializeView(Bundle savedInstanceState) {

    }

    @Override
    protected int getViewResource() {
        return R.layout.activity_set_timer;
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
        return setTimerRouter;
    }

    @Override
    protected BasePresenter getPresenter() {
        return setTimerPresenter;
    }

    @Override
    protected void inject() {
        setTimerComponent = DaggerSetTimerComponent.builder().
                applicationModule(new ApplicationModule(this)).build();
        setTimerComponent.inject(this);
    }

    @Override
    protected BaseComponent getActivityComponent() {
        return setTimerComponent;
    }
}

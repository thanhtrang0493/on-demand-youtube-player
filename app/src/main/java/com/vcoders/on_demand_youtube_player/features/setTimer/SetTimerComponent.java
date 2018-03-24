package com.vcoders.on_demand_youtube_player.features.setTimer;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.ApplicationModule;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface SetTimerComponent extends BaseComponent {
    Context context();

    void inject(SetTimerActivity setTimerActivity);
}

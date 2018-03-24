package com.vcoders.on_demand_youtube_player.features.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.ApplicationModule;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.playlistByTopic.PlaylistByTopicFragment;
import com.vcoders.on_demand_youtube_player.features.searchVideos.SearchVideosFragment;
import com.vcoders.on_demand_youtube_player.model.Topic;
import com.vcoders.on_demand_youtube_player.utils.Constant;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity implements HomeView {

    HomeComponent homeComponent;
    public static List<Topic> topics;

    @Inject
    HomePresenter homePresenter;

    @Inject
    HomeRouter homeRouter;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        getBundle();
    }

    private void getBundle() {
        topics = (List<Topic>) getIntent().getExtras().getSerializable(Constant.TOPICS);
        if (topics.size() > 0) {
            Topic topic = topics.get(0);
            topic.setSelect(true);
            topics.set(0, topic);
        }
    }

    @Override
    protected int getViewResource() {
        return R.layout.activity_home;
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
        return homeRouter;
    }

    @Override
    protected BasePresenter getPresenter() {
        return homePresenter;
    }

    @Override
    protected void inject() {
        homeComponent = DaggerHomeComponent.builder().
                applicationModule(new ApplicationModule(this)).build();
        homeComponent.inject(this);
    }

    @Override
    protected BaseComponent getActivityComponent() {
        return homeComponent;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void onSearchClick() {
        super.onSearchClick();
        changeFragment(new SearchVideosFragment(), null);
    }

    @Override
    public void onCloseClick() {
        super.onCloseClick();
        onBackPressed();
    }
}

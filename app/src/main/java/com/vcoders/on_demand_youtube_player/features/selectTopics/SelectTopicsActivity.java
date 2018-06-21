package com.vcoders.on_demand_youtube_player.features.selectTopics;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.SelectTopicsAdapter;
import com.vcoders.on_demand_youtube_player.architecture.ApplicationModule;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.model.Topic;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectTopicsActivity extends BaseActivity {

    @BindView(R.id.rvSelectTopics)
    RecyclerView rvSelectTopics;

    SelectTopicsAdapter adapter;
    SelectTopicsComponent selectTopicsComponent;

    @Inject
    SelectTopicsPresenter selectTopicsPresenter;

    @Inject
    SelectTopicsRouter selectTopicsRouter;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        initSelectTopicsAdapter();
    }

    private void initSelectTopicsAdapter() {
        List<Topic> topicList = selectTopicsPresenter.getListTopic();
        adapter = new SelectTopicsAdapter(this, topicList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvSelectTopics.setLayoutManager(manager);
        rvSelectTopics.setAdapter(adapter);
    }

    @Override
    protected int getViewResource() {
        return R.layout.activity_select_topics;
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
        return selectTopicsRouter;
    }

    @Override
    protected BasePresenter getPresenter() {
        return selectTopicsPresenter;
    }

    @Override
    protected void inject() {
        selectTopicsComponent = DaggerSelectTopicsComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        selectTopicsComponent.inject(this);
    }

    @Override
    protected BaseComponent getActivityComponent() {
        return selectTopicsComponent;
    }

    @OnClick(R.id.txtNext)
    public void onTxtNextClick() {
        List<Topic> selectTopics = adapter.listSelectTopic;
        selectTopicsPresenter.toHome(selectTopics);
    }

    @OnClick(R.id.txtSkip)
    public void onTxtSkipClick() {
        selectTopicsPresenter.toHome();
    }
}

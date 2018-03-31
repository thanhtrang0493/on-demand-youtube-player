package com.vcoders.on_demand_youtube_player.features.player;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.ListVideoAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class PlayerFragment extends BaseFragment<HomeComponent> implements PlayerView {

    @Inject
    PlayerPresenter playerPresenter;

    @Inject
    PlayerRouter playerRouter;

    ListVideoAdapter adapter;
    List<VideoYoutube> listVideo;
    int positionVideoSelected;

    @BindView(R.id.rvListVideo)
    RecyclerView rvListVideo;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        getBundle();
        initListVideoAdapter();
    }

    private void getBundle() {
        listVideo = new ArrayList<>();
        listVideo.add(new VideoYoutube());
        listVideo.add(new VideoYoutube());
        listVideo.add(new VideoYoutube());
        listVideo.add(new VideoYoutube());
        listVideo.add(new VideoYoutube());

        positionVideoSelected = 0;
    }

    private void initListVideoAdapter() {
        adapter = new ListVideoAdapter(getActivity(), listVideo, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvListVideo.setLayoutManager(manager);
        rvListVideo.setAdapter(adapter);
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_player;
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
    protected BasePresenter getPresenter() {
        return playerPresenter;
    }

    @Override
    protected BaseRouter getRouter() {
        return playerRouter;
    }

    @Override
    protected void inject() {
        ((HomeComponent) getAcitivyComponent()).inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void selectedItemVideo(int position) {

    }

    @Override
    public void selectedMoreItem(int position, LinearLayout layout) {

    }
}

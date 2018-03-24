package com.vcoders.on_demand_youtube_player.features.listVideo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.ListVideoAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.features.playlist.DialogAddPlaylist;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;
import com.vcoders.on_demand_youtube_player.utils.Constant;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class ListVideoFragment extends BaseFragment<HomeComponent> implements ListVideoView {

    @Inject
    ListVideoPresenter listVideoPresenter;

    ListVideoAdapter adapter;
    List<VideoYoutube> listVideo;

    @BindView(R.id.rvListVideo)
    RecyclerView rvListVideo;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        getBundle();
        initListVideoAdapter();
    }

    private void getBundle() {
        listVideo = (List<VideoYoutube>) getArguments().getSerializable(Constant.VIDEOS);
    }

    private void initListVideoAdapter() {
        adapter = new ListVideoAdapter(getActivity(), listVideo, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvListVideo.setLayoutManager(manager);
        rvListVideo.setAdapter(adapter);
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_list_video;
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
        return null;
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
    public void selectedMoreItem(int position, final ImageView imageView) {
        int[] imageCordinates = new int[2];
        imageView.getLocationOnScreen(imageCordinates);

        int x = imageCordinates[0];
        int y = imageCordinates[1];
        new DialogAddPlaylist(getActivity(), x, y).show();
    }

}

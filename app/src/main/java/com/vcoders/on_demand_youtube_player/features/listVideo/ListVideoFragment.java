package com.vcoders.on_demand_youtube_player.features.listVideo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.ListVideoAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeActivity;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.features.playlist.DialogAddPlaylist;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class ListVideoFragment extends BaseFragment<HomeComponent> implements ListVideoView {

    @Inject
    ListVideoPresenter listVideoPresenter;

    @Inject
    ListVideoRouter listVideoRouter;

    ListVideoAdapter adapter;
    List<Video> listVideo;
    EditText edtSearch;
    String searchName;
    String playlistId;

    @BindView(R.id.rvListVideo)
    RecyclerView rvListVideo;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        getBundle();

        getVideoFromPlaylistId();

        if ((HomeActivity) getActivity() != null) {
            edtSearch = ((HomeActivity) getActivity()).getEdtSearch();
        }

        initListVideoAdapter();
    }

    private void getVideoFromPlaylistId() {
        if (playlistId != null && !playlistId.isEmpty()) {
            listVideoPresenter.getVideoByPlaylistId(playlistId);
        }
    }

    private void getBundle() {
        listVideo = (List<Video>) getArguments().getSerializable(Constant.VIDEOS);
        searchName = getArguments().getString(Constant.SEARCH_NAME);
        playlistId = getArguments().getString(Constant.PLAYLIST_ID);

        if (searchName == null)
            searchName = "";

        if (listVideo == null)
            listVideo = new ArrayList<>();
    }

    private void initListVideoAdapter() {
        adapter = new ListVideoAdapter(getActivity(), listVideo, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvListVideo.setLayoutManager(manager);
        rvListVideo.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        edtSearch.setText(searchName);
        hideLLClose();
        setEventClickLLClose();
    }

    private void hideLLClose() {
        boolean isShow = false;
        if (edtSearch != null && !edtSearch.getText().toString().isEmpty())
            isShow = true;
        ((BaseActivity) getActivity()).showLLClose(isShow);
    }

    private void setEventClickLLClose() {
        ((BaseActivity) getActivity()).getLLClose().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSearch.setText("");
                listVideo = new ArrayList<>();
                ((Activity) getActivity()).onBackPressed();
            }
        });
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
        return new TypeActionBar[]{TypeActionBar.BACK, TypeActionBar.SEARCH_VIDEO};
    }

    @Override
    protected BasePresenter getPresenter() {
        return listVideoPresenter;
    }

    @Override
    protected BaseRouter getRouter() {
        return listVideoRouter;
    }

    @Override
    protected void inject() {
        ((HomeComponent) getAcitivyComponent()).inject(this);
    }

    @Override
    public void showError(String error) {
        Utils.getInstance().showError(getActivity(), error);
    }

    @Override
    public void showLoading(boolean isShow) {
        Utils.getInstance().showLoading(getActivity(), isShow);
    }

    @Override
    public void selectPlayVideo(int position) {
        listVideoPresenter.playVideo(listVideo, position);
    }

    @Override
    public void selectedMoreItem(int position, final LinearLayout layout) {
        int[] imageCordinates = new int[2];
        layout.getLocationOnScreen(imageCordinates);

        int x = imageCordinates[0];
        int y = imageCordinates[1];

        List<PlayList> myPlaylists = new ArrayList<>();
        myPlaylists.add(new PlayList("Favorite Songs"));
        myPlaylists.add(new PlayList("How-to"));
        myPlaylists.add(new PlayList("Education"));
        new DialogAddPlaylist(getActivity(), myPlaylists, this).show();
    }

    @Override
    public void getVideoByPlaylistSuccess(List<Video> videoYoutubeList) {
        if (videoYoutubeList != null) {
            this.listVideo = videoYoutubeList;
            adapter.updateAdapter(this.listVideo);
        }
    }

    @Override
    public void onAddNewPlaylist(int position) {
        listVideoPresenter.toLogin();
    }
}

package com.vcoders.on_demand_youtube_player.features.channels;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.ChannelsAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.features.playlist.PlaylistActivity;
import com.vcoders.on_demand_youtube_player.model.Channel;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChannelsActivity extends BaseActivity implements ChannelsView {

    private ChannelsPresenter channelsPresenter = new ChannelsPresenter(this);
    private ChannelsRouter channelsRouter = new ChannelsRouter();
    private List<Channel> channels = new ArrayList<>();
    private ChannelsAdapter channelsAdapter;

    @BindView(R.id.rvChannels)
    RecyclerView rvChannels;

    @Override
    protected void initializeView() {
        //init channel adapter
        channelsAdapter = channelsPresenter.initChannelAdapter(rvChannels, channels, this);

        //get playlist from channel id
        String channelId = "UCjwmbv6NE4mOh8Z8VhPUx1Q";
        channelsPresenter.getPlaylistFromChannelId(channelId);
    }

    @Override
    protected int setViewResource() {
        return R.layout.activity_channels;
    }

    @Override
    protected BasePresenter getPresenter() {
        return channelsPresenter;
    }

    @Override
    protected BaseRouter getRouter() {
        return channelsRouter;
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
    public void getPlaylistSuccess(List<Channel> channels) {
        this.channels = channels;
        channelsAdapter.updateAdapter(this.channels);
    }

    @Override
    public void selectChannelItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.PLAYLIST_ID, channels.get(position).getPlaylistId());
        Utils.getInstance().changeActivity(this, PlaylistActivity.class, bundle);
    }
}

package com.vcoders.on_demand_youtube_player.utils;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.features.home.HomeActivity;
import com.vcoders.on_demand_youtube_player.features.menu.MenuFragment;
import com.vcoders.on_demand_youtube_player.features.myPlaylist.MyPlaylistFragment;
import com.vcoders.on_demand_youtube_player.features.playlistByTopic.PlaylistByTopicFragment;

import java.io.Serializable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class ActionBarFooterUtils {

    Context context;

    @Nullable
    @BindView(R.id.txtHome)
    TextView txtHome;

    @Nullable
    @BindView(R.id.txtMyPlaylist)
    TextView txtMyPlaylist;

    @Nullable
    @BindView(R.id.txtMenu)
    TextView txtMenu;

    @Inject
    public ActionBarFooterUtils(Context context) {
        this.context = context;
    }

    public void setView(View view) {
        ButterKnife.bind(this, view);
    }

    @Optional
    @Nullable
    @OnClick(R.id.llHome)
    public void onLLHomeClick() {
        if ((BaseActivity) context != null) {
            selectTabFooter(0);

            ((BaseActivity) context).changeFragment(new PlaylistByTopicFragment(), new Bundle());
        }
    }

    @Optional
    @Nullable
    @OnClick(R.id.llMyPlaylist)
    public void onLLMyPlaylistClick() {
        if ((BaseActivity) context != null) {
            selectTabFooter(1);

            ((BaseActivity) context).changeFragment(new MyPlaylistFragment(), null);
        }
    }

    @Optional
    @Nullable
    @OnClick(R.id.llMenu)
    public void onLLMenuClick() {
        if ((BaseActivity) context != null) {
            selectTabFooter(2);

            ((BaseActivity) context).changeFragment(new MenuFragment(), null);
        }
    }

    public void selectTabFooter(int index) {
        if (txtHome != null && txtMyPlaylist != null && txtMenu != null) {
            txtHome.setTextColor(context.getResources().getColor(R.color.gray));
            txtMyPlaylist.setTextColor(context.getResources().getColor(R.color.gray));
            txtMenu.setTextColor(context.getResources().getColor(R.color.gray));
            switch (index) {
                case 0:
                    txtHome.setTextColor(context.getResources().getColor(R.color.white));
                    break;
                case 1:
                    txtMyPlaylist.setTextColor(context.getResources().getColor(R.color.white));
                    break;
                case 2:
                    txtMenu.setTextColor(context.getResources().getColor(R.color.white));
                    break;
            }
        }
    }
}

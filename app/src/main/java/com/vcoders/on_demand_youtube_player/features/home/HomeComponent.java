package com.vcoders.on_demand_youtube_player.features.home;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.ApplicationModule;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;
import com.vcoders.on_demand_youtube_player.features.listVideo.ListVideoFragment;
import com.vcoders.on_demand_youtube_player.features.myPlaylist.MyPlaylistFragment;
import com.vcoders.on_demand_youtube_player.features.player.PlayerFragment;
import com.vcoders.on_demand_youtube_player.features.playlistByTopic.PlaylistByTopicFragment;
import com.vcoders.on_demand_youtube_player.features.playlistDetail.PlaylistDetailFragment;
import com.vcoders.on_demand_youtube_player.features.searchVideos.SearchVideosFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface HomeComponent extends BaseComponent {
    Context context();

    void inject(HomeActivity homeActivity);

    void inject(PlaylistByTopicFragment playlistByTopicFragment);

    void inject(SearchVideosFragment searchVideosFragment);

    void inject(ListVideoFragment listVideoFragment);

    void inject(MyPlaylistFragment myPlaylistFragment);

    void inject(PlaylistDetailFragment playlistDetailFragment);

    void inject(PlayerFragment playerFragment);
}

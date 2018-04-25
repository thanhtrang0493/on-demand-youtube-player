package com.vcoders.on_demand_youtube_player.features.playlist;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.ListMyPlaylistAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseDialog;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectItem;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.utils.Utils;
import com.vcoders.on_demand_youtube_player.utils.UtilsConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class DialogAddPlaylist extends BaseDialog implements ISelectItem {

    Context context;
    List<PlayList> playLists;
    ListMyPlaylistAdapter adapter;

    public DialogAddPlaylist(Context context, List<PlayList> playLists) {
        super(context);
        this.context = context;
        this.playLists = playLists;
    }

    @Override
    protected int getViewResource() {
        return R.layout.dialog_add_playlist;
    }

    @Override
    protected void initializeView() {
        initMyPlaylistAdapter();
    }

    @Override
    protected void setupDialog() {
        UtilsConfig.getInstance().configDialogCenter(this);
    }

    @BindView(R.id.rvMyPlaylist)
    RecyclerView rvMyPlaylist;

    @OnClick(R.id.llCreateNewPlaylist)
    public void onLlCreateNewPlaylistClick() {
        this.dismiss();
        new DialogAddNewPlaylist(context).show();
    }

    private void initMyPlaylistAdapter() {
        adapter = new ListMyPlaylistAdapter(context, playLists, this);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rvMyPlaylist.setLayoutManager(manager);
        rvMyPlaylist.setAdapter(adapter);
    }

    @Override
    public void onSelectedItem(int position) {

    }
}

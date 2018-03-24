package com.vcoders.on_demand_youtube_player.features.playlist;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BaseDialog;


public class DialogAddNewPlaylist extends BaseDialog {

    public DialogAddNewPlaylist(Context context) {
        super(context);
    }

    @Override
    protected int getViewResource() {
        return R.layout.dialog_add_new_playlist;
    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void setupDialog() {

    }
}

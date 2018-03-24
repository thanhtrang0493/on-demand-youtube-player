package com.vcoders.on_demand_youtube_player.features.playlist;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BaseDialog;


public class DialogAddPlaylist extends BaseDialog {

    Context context;
    int x;
    int y;

    public DialogAddPlaylist(Context context, int x, int y) {
        super(context);
        this.context = context;
        this.x = x;
        this.y = y;
    }

    @Override
    protected int getViewResource() {
        return R.layout.dialog_add_playlist;
    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void setupDialog() {
        setupDialogRight();
    }

    private void setupDialogRight() {
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setGravity(Gravity.RIGHT);
//        getWindow().getAttributes().gravity = Gravity.RIGHT;

        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
//        wmlp.gravity = Gravity.RIGHT;
        wmlp.x = x;
        wmlp.y = y;
        getWindow().setAttributes(wmlp);

//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        getWindow().setGravity(Gravity.RIGHT);
        setCancelable(true);

        //The below code is EXTRA - to dim the parent view by 70%
        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.dimAmount = 1f;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        getWindow().setAttributes(lp);
    }

}

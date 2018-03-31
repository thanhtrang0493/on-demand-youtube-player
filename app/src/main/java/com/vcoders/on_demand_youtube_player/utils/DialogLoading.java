package com.vcoders.on_demand_youtube_player.utils;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;

import com.vcoders.on_demand_youtube_player.R;

import javax.inject.Inject;

public class DialogLoading {
    Dialog dialog;
    Context context;

    @Inject
    public DialogLoading(Context context) {
        this.context = context;
        setUpDialog();
    }

    public void setUpDialog(){
        dialog = new Dialog(context, android.support.design.R.style.Base_Theme_AppCompat_Light_DialogWhenLarge);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
    }

    public void show(){
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }
    public void show(boolean isShow){
        if (isShow)
            show();
        else
            dismiss();
    }
}

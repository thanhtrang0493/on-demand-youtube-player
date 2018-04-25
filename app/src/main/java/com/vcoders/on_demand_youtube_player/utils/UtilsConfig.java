package com.vcoders.on_demand_youtube_player.utils;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class UtilsConfig {
    private static final UtilsConfig ourInstance = new UtilsConfig();

    public static UtilsConfig getInstance() {
        return ourInstance;
    }

    private UtilsConfig() {
    }

    public void configDialogCenter(Dialog dialog) {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);

        //The below code is EXTRA - to dim the parent view by 70%
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.5f;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        dialog.getWindow().setAttributes(lp);
    }

    private void setupDialogRight(Dialog dialog, int x, int y) {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setGravity(Gravity.RIGHT);
//        getWindow().getAttributes().gravity = Gravity.RIGHT;

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
//        wmlp.gravity = Gravity.RIGHT;
        wmlp.x = x;
        wmlp.y = y;
        dialog.getWindow().setAttributes(wmlp);

//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        getWindow().setGravity(Gravity.RIGHT);
        dialog.setCancelable(true);

        //The below code is EXTRA - to dim the parent view by 70%
        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.dimAmount = 1f;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        getWindow().setAttributes(lp);
    }
}

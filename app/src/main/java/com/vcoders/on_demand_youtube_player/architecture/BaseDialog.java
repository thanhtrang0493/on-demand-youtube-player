package com.vcoders.on_demand_youtube_player.architecture;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;


public abstract class BaseDialog extends Dialog implements DialogInterface.OnKeyListener {
    public BaseDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getViewResource());
        ButterKnife.bind(this);
        setupDialog();
        initializeView();
        this.setCanceledOnTouchOutside(false);
        this.setOnKeyListener(this);
    }

    private void setupDialog1() {
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        getWindow().getAttributes().gravity = Gravity.CENTER;

        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(wmlp);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(true);

        //The below code is EXTRA - to dim the parent view by 70%
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0.8f;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        getWindow().setAttributes(lp);
    }

    private void setupDialogRight() {
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.RIGHT);
        getWindow().getAttributes().gravity = Gravity.RIGHT;

        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        wmlp.gravity = Gravity.RIGHT;
        wmlp.x = 30;
        wmlp.y = 70;
        getWindow().setAttributes(wmlp);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.RIGHT);
        setCancelable(true);

        //The below code is EXTRA - to dim the parent view by 70%
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0.8f;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        getWindow().setAttributes(lp);
    }

    protected abstract int getViewResource();

    protected abstract void initializeView();

    protected abstract void setupDialog();

    @Override
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        //doesn't dismiss dialog on back button pressed
//        return i == KeyEvent.KEYCODE_BACK;
        return false;
    }
}
package com.vcoders.on_demand_youtube_player.features.playlist;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BaseDialog;
import com.vcoders.on_demand_youtube_player.utils.UtilsConfig;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;


public class DialogAddNewPlaylist extends BaseDialog {

    Context context;

    public DialogAddNewPlaylist(Context context) {
        super(context);
        this.context = context;
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
        UtilsConfig.getInstance().configDialogCenter(this);
    }

    @BindView(R.id.txtCancel)
    TextView txtCancel;

    @BindView(R.id.txtOK)
    TextView txtOK;

    @OnTextChanged(R.id.edtTitle)
    public void onEdtTitleTextChanged(CharSequence s) {
        if (s.toString().isEmpty()) {
            txtOK.setTextColor(context.getResources().getColor(R.color.gray_8e));
        } else {
            txtOK.setTextColor(context.getResources().getColor(R.color.gray_regular));
        }
    }

    @OnClick(R.id.txtCancel)
    public void onTxtCancelClick() {
        this.dismiss();
    }

    @OnClick(R.id.txtOK)
    public void onTxtOKClick() {
        this.dismiss();
    }
}

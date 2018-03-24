package com.vcoders.on_demand_youtube_player.utils;


import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActionBarHeaderUtils {

    Context context;

    @BindView(R.id.edtSearch)
    public EditText edtSearch;

    @BindView(R.id.llSearch)
    LinearLayout llSearch;

    @BindView(R.id.llClose)
    LinearLayout llClose;

    @BindView(R.id.llBack)
    LinearLayout llBack;

    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @Inject
    public ActionBarHeaderUtils(Context context) {
        this.context = context;
    }

    public void setView(View view) {
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.llSearch)
    public void onLlSearchClick() {
        txtTitle.setVisibility(View.GONE);
        llSearch.setVisibility(View.GONE);
        llClose.setVisibility(View.VISIBLE);
        edtSearch.setVisibility(View.VISIBLE);

        if ((BaseActivity) context != null) {
            ((BaseActivity) context).onSearchClick();
        }
    }

    @OnClick(R.id.llClose)
    public void onLLCloseClick() {
        txtTitle.setVisibility(View.VISIBLE);
        llSearch.setVisibility(View.VISIBLE);
        llClose.setVisibility(View.GONE);
        edtSearch.setVisibility(View.GONE);

        if ((BaseActivity) context != null) {
            ((BaseActivity) context).onCloseClick();
        }
    }
}

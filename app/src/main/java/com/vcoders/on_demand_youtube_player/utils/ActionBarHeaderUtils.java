package com.vcoders.on_demand_youtube_player.utils;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;

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
    public LinearLayout llClose;

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

    private void hideActionbar() {
        llBack.setVisibility(View.GONE);
        llSearch.setVisibility(View.GONE);
        llClose.setVisibility(View.GONE);
        edtSearch.setVisibility(View.GONE);
        txtTitle.setVisibility(View.VISIBLE);
    }

    public void setTitle(String title) {
        txtTitle.setText(title);
    }

    public void showTypeActionbar(TypeActionBar[] typeActionBars) {
        hideActionbar();
        if (typeActionBars != null) {
            for (TypeActionBar type : typeActionBars) {
                switch (type) {
                    case BACK:
                        llBack.setVisibility(View.VISIBLE);
                        break;
                    case CLOSE:
                        llClose.setVisibility(View.VISIBLE);
                        break;
                    case SEARCH:
                        llSearch.setVisibility(View.VISIBLE);
                        break;
                    case SEARCH_VIDEO:
                        edtSearch.setVisibility(View.VISIBLE);
                        txtTitle.setVisibility(View.GONE);
                        break;
                }
            }
        }
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

    @OnClick(R.id.llBack)
    public void onLLBackClick() {
        ((Activity) context).onBackPressed();
    }

    public void showLLClose(boolean isShow) {
        if (llClose != null) {
            if (isShow)
                llClose.setVisibility(View.VISIBLE);
            else
                llClose.setVisibility(View.GONE);
        }
    }
}

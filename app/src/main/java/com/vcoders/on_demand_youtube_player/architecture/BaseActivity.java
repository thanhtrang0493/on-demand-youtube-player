package com.vcoders.on_demand_youtube_player.architecture;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.utils.ActionBarFooterUtils;
import com.vcoders.on_demand_youtube_player.utils.ActionBarHeaderUtils;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity {

    @Inject
    ActionBarFooterUtils actionBarFooterUtils;

    @Inject
    public ActionBarHeaderUtils actionBarHeaderUtils;

    @Nullable
    @BindView(R.id.llHeader)
    View llHeader;

    @Nullable
    @BindView(R.id.llFooter)
    View llFooter;

    @Nullable
    @BindView(R.id.frContainer)
    FrameLayout frContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewResource());
        ButterKnife.bind(this);

        if (getPresenter() != null) {
            getPresenter().setView(this);
            getPresenter().setRouter(getRouter());
        }

        inject();

        initializeView(savedInstanceState);

        setupActionbar();
    }


    public void changeFragment(Fragment fragment, Bundle bundle) {
        String backStateName = fragment.getClass().getName();
        boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate(backStateName, 0);
        if (frContainer != null && !fragmentPopped) {
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frContainer, fragment);
            transaction.addToBackStack(backStateName);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
//            FragmentManager fm = getActivity()
//                    .getSupportFragmentManager();
//            fm.popBackStack ("fragB", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            super.onBackPressed();
        }
    }

    private void setupActionbar() {
        if (llHeader != null) {
            actionBarHeaderUtils.setView(llHeader);
            actionBarHeaderUtils.showTypeActionbar(getTypeActionBar());
            actionBarHeaderUtils.setTitle(getTitleActionBar());
        }

        if (llFooter != null) {
            actionBarFooterUtils.setView(llFooter);

            //change fragment playlist
            actionBarFooterUtils.onLLHomeClick();
        }
    }

    public LinearLayout getLLClose() {
        return actionBarHeaderUtils.llClose;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getPresenter() != null) {
            getPresenter().setView(this);
            getPresenter().setRouter(getRouter());
        }

        try {
            EventBus.getDefault().register(getPresenter());
        } catch (Exception e) {

        }
    }


    public void onSearchClick() {

    }

    public void onCloseClick() {

    }

    public EditText getEdtSearch() {
        return actionBarHeaderUtils.edtSearch;
    }

    @Override
    protected void onStop() {
        super.onStop();

        try {
            EventBus.getDefault().unregister(getPresenter());
        } catch (Exception e) {

        }
    }

    public void showLLClose(boolean isShow) {
        if (actionBarHeaderUtils != null)
            actionBarHeaderUtils.showLLClose(isShow);
    }

    protected abstract void initializeView(Bundle savedInstanceState);

    protected abstract int getViewResource();

    protected abstract String getTitleActionBar();

    protected abstract TypeActionBar[] getTypeActionBar();

    protected abstract BaseRouter getRouter();

    protected abstract BasePresenter getPresenter();

    protected abstract void inject();

    protected abstract BaseComponent getActivityComponent();
}

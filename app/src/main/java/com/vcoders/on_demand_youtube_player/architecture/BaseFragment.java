package com.vcoders.on_demand_youtube_player.architecture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;

import butterknife.ButterKnife;


public abstract class BaseFragment<ActivityComponent> extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {

            view = inflater.inflate(getViewResource(), null);

            ButterKnife.bind(this, view);

            inject();

            //init view
            initializeView(savedInstanceState);
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getPresenter() != null) {
            getPresenter().setView(this);
            getPresenter().setRouter(((BaseActivity) getActivity()).getRouter());
        }
    }

    protected ActivityComponent getAcitivyComponent() {
        return (ActivityComponent) ((BaseActivity) getActivity()).getActivityComponent();
    }

    protected abstract void initializeView(Bundle savedInstanceState);

    protected abstract int getViewResource();

    protected abstract String getTitleActionBar();

    protected abstract TypeActionBar[] getTypeActionBar();

    protected abstract BasePresenter getPresenter();

    protected abstract void inject();
}
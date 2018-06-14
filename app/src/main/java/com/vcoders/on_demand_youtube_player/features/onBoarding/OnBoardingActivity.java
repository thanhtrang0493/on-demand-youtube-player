package com.vcoders.on_demand_youtube_player.features.onBoarding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.OnBoardingAdapter;
import com.vcoders.on_demand_youtube_player.architecture.ApplicationModule;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.utils.Utils;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class OnBoardingActivity extends BaseActivity implements OnBoardingView {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    OnBoardingAdapter onBoardingAdapter;
    OnBoardingComponent onBoardingComponent;
    List<LinearLayout> linearLayouts;
    int indexPage = 0;

    @BindView(R.id.llCircle1)
    LinearLayout llCircle1;

    @BindView(R.id.llCircle2)
    LinearLayout llCircle2;

    @BindView(R.id.llCircle3)
    LinearLayout llCircle3;

    @Inject
    OnBoardingRouter onBoardingRouter;

    @Inject
    OnBoardingPresenter onBoardingPresenter;

    @BindView(R.id.txtNext)
    TextView txtNext;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        initOnBoardingAdapter();
        getListCircle();
    }

    private void getListCircle() {
        linearLayouts = new ArrayList<>();
        linearLayouts.add(llCircle1);
        linearLayouts.add(llCircle2);
        linearLayouts.add(llCircle3);
    }

    private void initOnBoardingAdapter() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(OnBoardingFragment.newInstance("Layout 1"));
        fragments.add(OnBoardingFragment.newInstance("Layout 2"));
        fragments.add(OnBoardingFragment.newInstance("Layout 3"));
        onBoardingAdapter = new OnBoardingAdapter(getSupportFragmentManager(), fragments, viewPager, this);
        viewPager.setAdapter(onBoardingAdapter);
    }

    @Override
    protected int getViewResource() {
        return R.layout.activity_on_boarding;
    }

    @Override
    protected String getTitleActionBar() {
        return null;
    }

    @Override
    protected TypeActionBar[] getTypeActionBar() {
        return new TypeActionBar[0];
    }

    @Override
    protected BaseRouter getRouter() {
        return onBoardingRouter;
    }

    @Override
    protected BasePresenter getPresenter() {
        return onBoardingPresenter;
    }

    @Override
    protected void inject() {
        onBoardingComponent = DaggerOnBoardingComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        onBoardingComponent.inject(this);
    }

    @Override
    protected BaseComponent getActivityComponent() {
        return onBoardingComponent;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void onPageSelected(int position) {
        indexPage = position;
        onBoardingPresenter.selectPage(position, linearLayouts, txtNext);
    }

    @OnClick(R.id.txtNext)
    public void onTxtNextClick() {
        onBoardingPresenter.nextPage(indexPage, linearLayouts, viewPager, txtNext);
    }

    @OnClick(R.id.txtSkip)
    public void onTextSkipClick() {
        onBoardingPresenter.skipPage();
    }

}

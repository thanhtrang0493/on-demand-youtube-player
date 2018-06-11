package com.vcoders.on_demand_youtube_player.features.onBoarding;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;

import java.util.List;

import javax.inject.Inject;


public class OnBoardingPresenter extends BasePresenter<OnBoardingView, OnBoardingRouter> {

    Context context;

    @Inject
    public OnBoardingPresenter(Context context) {
        this.context = context;
    }

    public void selectPage(int position, List<LinearLayout> linearLayouts, TextView txtNext) {
        for (int i = 0; i < linearLayouts.size(); i++) {
            if (position == i) {
                linearLayouts.get(i).setBackgroundResource(R.drawable.circle_gray_select);
            } else {
                linearLayouts.get(i).setBackgroundResource(R.drawable.circle_gray_unselect);
            }

            if (position == linearLayouts.size() - 1)
                txtNext.setText(context.getString(R.string.get_started));
            else
                txtNext.setText(context.getString(R.string.next));
        }
    }

    public void nextPage(int positionPage, List<LinearLayout> linearLayouts, ViewPager viewPager, TextView txtNext) {
        if (positionPage == linearLayouts.size() - 1) {
            getRouter().toSelectTopic();
            ((Activity) context).finish();
        } else {
            viewPager.setCurrentItem(positionPage);
            selectPage(positionPage, linearLayouts, txtNext);
        }
    }

    public void skipPage() {
        getRouter().toSelectTopic();
        ((Activity) context).finish();
    }
}

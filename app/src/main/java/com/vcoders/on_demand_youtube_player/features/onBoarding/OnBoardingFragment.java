package com.vcoders.on_demand_youtube_player.features.onBoarding;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnBoardingFragment extends Fragment {
    public static OnBoardingFragment newInstance(String description) {
        OnBoardingFragment onBoardingFragment = new OnBoardingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.DESCRIPTION, description);
        onBoardingFragment.setArguments(bundle);
        return onBoardingFragment;
    }

    String description;

    @BindView(R.id.txtDescription)
    TextView txtDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_on_boarding, container, false);
        ButterKnife.bind(this, view);

        getBundle();

        bindData();

        return view;
    }

    private void getBundle() {
        description = getArguments().getString(Constant.DESCRIPTION);
    }

    private void bindData() {
        txtDescription.setText(description);
    }

}

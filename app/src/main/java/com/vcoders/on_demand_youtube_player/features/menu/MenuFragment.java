package com.vcoders.on_demand_youtube_player.features.menu;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.model.User;
import com.vcoders.on_demand_youtube_player.utils.AccountUtils;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MenuFragment extends BaseFragment<HomeComponent> implements MenuView {

    @Inject
    MenuPresenter menuPresenter;

    @Inject
    MenuRouter menuRouter;

    @BindView(R.id.txtUserName)
    TextView txtUserName;

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @BindView(R.id.svUser)
    ScrollView svUser;

    @BindView(R.id.llLogin)
    RelativeLayout llLogin;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        if (AccountUtils.getInstance().isLogin()) {
            menuPresenter.getUserProfile();
            llLogin.setVisibility(View.GONE);
            svUser.setVisibility(View.VISIBLE);
        } else {
            llLogin.setVisibility(View.VISIBLE);
            svUser.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_menu;
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
    protected BasePresenter getPresenter() {
        return menuPresenter;
    }

    @Override
    protected BaseRouter getRouter() {
        return menuRouter;
    }

    @Override
    protected void inject() {
        ((HomeComponent) getAcitivyComponent()).inject(this);
    }

    @Override
    public void onUpdateUserInfo(User user) {
        if (user != null) {
            txtUserName.setText(user.getName());
            Picasso.with(getActivity()).load(user.getPicture()).into(imgAvatar);
        }
    }

    @Override
    public void onLogoutSuccess() {
        llLogin.setVisibility(View.VISIBLE);
        svUser.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Utils.getInstance().showError(getActivity(), error);
    }

    @Override
    public void showLoading(boolean isShow) {
        Utils.getInstance().showLoading(getActivity(), isShow);
    }

    @OnClick(R.id.txtLogout)
    public void onLogoutClick() {
        menuPresenter.logout();
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClick() {
        menuPresenter.toLogin();
    }
}

package com.vcoders.on_demand_youtube_player.features.menu;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.interactor.GetMyPlaylist;
import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.User;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;

import javax.inject.Inject;

import butterknife.BindView;

public class MenuFragment extends BaseFragment<HomeComponent> implements MenuView {

    @Inject
    MenuPresenter menuPresenter;

    @Inject
    MenuRouter menuRouter;

    @BindView(R.id.txtUserName)
    TextView txtUserName;

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        menuPresenter.getUserProfile();

        new GetMyPlaylist(getActivity()).execute().onListener(new RequestAPIListener<Data<Video>>() {
            @Override
            public void onResponse(ResponseAPIListener<Data<Video>> response) {
                if(response.getErrorMessage()==null){

                }
            }
        });
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
    public void showError(String error) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }
}

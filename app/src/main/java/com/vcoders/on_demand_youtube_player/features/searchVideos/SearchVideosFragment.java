package com.vcoders.on_demand_youtube_player.features.searchVideos;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.SearchVideoAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeActivity;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class SearchVideosFragment extends BaseFragment<HomeComponent> {

    SearchVideoAdapter adapter;
    List<VideoYoutube> listVideo;
    EditText edtSearch;

    @Inject
    SearchVideosPresenter searchVideosPresenter;

    @BindView(R.id.rvSearchVideos)
    RecyclerView rvSearchVideos;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        if ((HomeActivity) getActivity() != null)
            edtSearch = ((HomeActivity) getActivity()).getEdtSearch();

        listVideo = new ArrayList<>();
        initSearchVideo();
        initOnKeyListener();
        initSearchVideosAdapter();
    }

    private void initSearchVideosAdapter() {
        adapter = new SearchVideoAdapter(getActivity(), listVideo);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvSearchVideos.setLayoutManager(manager);
        rvSearchVideos.setAdapter(adapter);
    }

    private void initSearchVideo() {
        if (edtSearch != null) {
            edtSearch.requestFocus();
            edtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    listVideo = searchVideosPresenter.searchVideosByName(charSequence.toString());
                    adapter.updateAdapter(listVideo);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    private void initOnKeyListener() {
        if (edtSearch != null) {
            edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ( (actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                            (event.getAction() == KeyEvent.ACTION_DOWN ))   && !edtSearch.getText().toString().isEmpty()){
                        searchVideosPresenter.toDisplayListVideo(listVideo);
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            });
        }

    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_search_video;
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
        return searchVideosPresenter;
    }

    @Override
    protected void inject() {
        ((HomeComponent) getAcitivyComponent()).inject(this);
    }

}

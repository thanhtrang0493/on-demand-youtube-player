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
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeActivity;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;
import com.vcoders.on_demand_youtube_player.utils.DialogLoading;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class SearchVideosFragment extends BaseFragment<HomeComponent> implements SearchVideosView {

    SearchVideoAdapter adapter;
    List<String> nameList;
    EditText edtSearch;

    @Inject
    SearchVideosPresenter searchVideosPresenter;

    @BindView(R.id.rvSearchVideos)
    RecyclerView rvSearchVideos;

    @Inject
    SearchVideosRouter searchVideosRouter;

    @Inject
    DialogLoading dialogLoading;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        if ((HomeActivity) getActivity() != null)
            edtSearch = ((HomeActivity) getActivity()).getEdtSearch();

        nameList = new ArrayList<>();
        initSearchVideo();
        initOnKeyListener();
        initSearchVideosAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideLLClose();
        setEventClickLLClose();
    }

    private void hideLLClose() {
        boolean isShow = false;
        if (edtSearch != null && !edtSearch.getText().toString().isEmpty())
            isShow = true;
        ((BaseActivity) getActivity()).showLLClose(isShow);
    }

    private void setEventClickLLClose() {
        ((BaseActivity) getActivity()).getLLClose().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSearch.setText("");
                nameList = new ArrayList<>();
                adapter.updateAdapter(nameList);
            }
        });
    }

    private void initSearchVideosAdapter() {
        adapter = new SearchVideoAdapter(getActivity(), nameList, this);
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
                    if ((BaseActivity) getActivity() != null) {
                        if (charSequence.toString().isEmpty())
                            ((BaseActivity) getActivity()).showLLClose(false);
                        else
                            ((BaseActivity) getActivity()).showLLClose(true);
                    }

                    searchVideosPresenter.searchSuggestName(charSequence.toString());
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
                    if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                            (event.getAction() == KeyEvent.ACTION_DOWN)) && !edtSearch.getText().toString().isEmpty()) {
                        searchVideosPresenter.searchVideoByName(edtSearch.getText().toString());
                        return true;
                    } else {
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
        return new TypeActionBar[]{TypeActionBar.BACK, TypeActionBar.SEARCH_VIDEO};
    }

    @Override
    protected BasePresenter getPresenter() {
        return searchVideosPresenter;
    }

    @Override
    protected BaseRouter getRouter() {
        return searchVideosRouter;
    }

    @Override
    protected void inject() {
        ((HomeComponent) getAcitivyComponent()).inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoading(boolean isShow) {
        dialogLoading.show(isShow);
    }

    @Override
    public void searchVideoSuccess(List<VideoYoutube> videoList, String search) {
        searchVideosPresenter.toDisplayListVideo(videoList, search);
    }

    @Override
    public void searchSuggestNameSuccess(List<String> nameList) {
        this.nameList = nameList;
        adapter.updateAdapter(this.nameList);
    }

    @Override
    public void onSelectItemSuggestName(int position) {
        searchVideosPresenter.searchVideoByName(nameList.get(position));
    }
}

package com.vcoders.on_demand_youtube_player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.ArrayList;
import java.util.List;


public class SearchVideoAdapter extends RecyclerView.Adapter<SearchVideoAdapter.SearchVideoHolder> {

    Context context;
    List<VideoYoutube> listVideo;

    public SearchVideoAdapter(Context context, List<VideoYoutube> listVideo) {
        this.context = context;
        this.listVideo = listVideo;
        if (this.listVideo == null)
            this.listVideo = new ArrayList<>();
    }

    public void updateAdapter(List<VideoYoutube> listVideo) {
        this.listVideo = listVideo;
        if (this.listVideo == null)
            this.listVideo = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public SearchVideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_video, parent, false);
        return new SearchVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchVideoHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listVideo.size();
    }


    public class SearchVideoHolder extends RecyclerView.ViewHolder {
        public SearchVideoHolder(View itemView) {
            super(itemView);
        }
    }
}

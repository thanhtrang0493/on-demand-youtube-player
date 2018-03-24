package com.vcoders.on_demand_youtube_player.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.features.listVideo.ListVideoView;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectVideo;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.ArrayList;
import java.util.List;

public class ListVideoAdapter extends RecyclerView.Adapter<ListVideoAdapter.ListVideoHolder> {

    Context context;
    List<VideoYoutube> listVideo;
    ISelectVideo listener;

    public ListVideoAdapter(Context context, List<VideoYoutube> listVideo, ISelectVideo listener) {
        this.context = context;
        this.listener = listener;
        this.listVideo = listVideo;
        if (this.listVideo == null)
            this.listVideo = new ArrayList<>();
    }

    @Override
    public ListVideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_video, parent, false);
        return new ListVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListVideoHolder holder, final int position) {
        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.selectedMoreItem(position, holder.imgMore);
            }
        });

        holder.llItemVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.selectedItemVideo(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listVideo.size();
    }

    public class ListVideoHolder extends RecyclerView.ViewHolder {
        private ImageView imgMore;
        private LinearLayout llItemVideo;

        public ListVideoHolder(View itemView) {
            super(itemView);
            imgMore = (ImageView) itemView.findViewById(R.id.imgMore);
            llItemVideo = (LinearLayout) itemView.findViewById(R.id.llItemVideo);
        }
    }
}

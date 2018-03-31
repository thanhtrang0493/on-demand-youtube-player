package com.vcoders.on_demand_youtube_player.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.features.listVideo.ListVideoView;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectVideo;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;
import com.vcoders.on_demand_youtube_player.utils.Utils;

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
        bindData(holder, listVideo.get(position));

        holder.llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.selectedMoreItem(position, holder.llMore);
            }
        });

        holder.llItemVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.selectedItemVideo(position);
            }
        });
    }

    private void bindData(ListVideoHolder holder, VideoYoutube video) {
        String time = context.getResources().getString(R.string.today);
        int days = Utils.getInstance().calculateDays(video.getPublishedAt());
        if (days > 0) {
            if (days < 365) {
                time = Utils.getInstance().convertIntToString(days) + " " +
                        context.getResources().getString(R.string.days_ago);
            } else {
                int years = days / 365;
                time = Utils.getInstance().convertIntToString(years) + " " +
                        context.getResources().getString(R.string.years_ago);
            }
        }

        Picasso.with(context).load(video.getThumbnails()).into(holder.imgThumbnails);
        holder.txtTime.setText(video.getTime());
        holder.txtTitle.setText(video.getTitle());
        holder.txtChannelTitle.setText(video.getChannelTitle());
        holder.txtPublishedAt.setText(time);
        holder.txtViewCount.setText(video.getViewCount());
    }


    @Override
    public int getItemCount() {
        return listVideo.size();
    }

    public class ListVideoHolder extends RecyclerView.ViewHolder {
        private LinearLayout llMore;
        private LinearLayout llItemVideo;
        private ImageView imgThumbnails;
        private TextView txtTime;
        private TextView txtTitle;
        private TextView txtChannelTitle;
        private TextView txtPublishedAt;
        private TextView txtViewCount;

        public ListVideoHolder(View itemView) {
            super(itemView);
            llMore = (LinearLayout) itemView.findViewById(R.id.llMore);
            llItemVideo = (LinearLayout) itemView.findViewById(R.id.llItemVideo);
            imgThumbnails = (ImageView) itemView.findViewById(R.id.imgThumbnails);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtChannelTitle = (TextView) itemView.findViewById(R.id.txtChannelTitle);
            txtPublishedAt = (TextView) itemView.findViewById(R.id.txtPublishedAt);
            txtViewCount = (TextView) itemView.findViewById(R.id.txtViewCount);
        }
    }
}

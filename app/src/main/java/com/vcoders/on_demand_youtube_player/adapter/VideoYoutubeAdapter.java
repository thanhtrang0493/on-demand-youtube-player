package com.vcoders.on_demand_youtube_player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.features.playlist.PlaylistView;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.ArrayList;
import java.util.List;

public class VideoYoutubeAdapter extends RecyclerView.Adapter<VideoYoutubeAdapter.VideoYoutubeHolder> {

    private Context context;
    private List<VideoYoutube> videoYoutubes;
    private PlaylistView playlistView;

    public VideoYoutubeAdapter(Context context, List<VideoYoutube> videoYoutubes, PlaylistView playlistView) {
        this.context = context;
        this.playlistView = playlistView;
        this.videoYoutubes = videoYoutubes;
        if (this.videoYoutubes == null)
            this.videoYoutubes = new ArrayList<>();
    }

    public void updateAdapter(List<VideoYoutube> videoYoutubes) {
        this.videoYoutubes = videoYoutubes;
        notifyDataSetChanged();
    }

    @Override
    public VideoYoutubeAdapter.VideoYoutubeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_youtube_item, parent, false);
        return new VideoYoutubeHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoYoutubeAdapter.VideoYoutubeHolder holder, final int position) {
        VideoYoutube videoYoutube = videoYoutubes.get(position);
        holder.tvTitle.setText(videoYoutube.getTitle());
        Picasso.with(context).load(videoYoutube.getThumbnails()).into(holder.imgThumbnail);
        holder.llVideoYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playlistView.playVideoYoutube(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoYoutubes.size();
    }

    public class VideoYoutubeHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView imgThumbnail;
        private LinearLayout llVideoYoutube;

        public VideoYoutubeHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.imageViewThumbnail);
            llVideoYoutube = (LinearLayout) itemView.findViewById(R.id.llVideoYoutube);
        }
    }
}
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
import com.vcoders.on_demand_youtube_player.features.example.channels.ChannelsView;
import com.vcoders.on_demand_youtube_player.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ChannelsHolder> {

    private Context context;
    private List<Channel> channels;
    private ChannelsView channelsView;

    public ChannelsAdapter(Context context, List<Channel> channels, ChannelsView channelsView) {
        this.context = context;
        this.channels = channels;
        this.channelsView = channelsView;
        if (this.channels == null)
            this.channels = new ArrayList<>();
    }

    public void updateAdapter(List<Channel> channels) {
        this.channels = channels;
        notifyDataSetChanged();
    }

    @Override
    public ChannelsAdapter.ChannelsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.channel_item, parent, false);
        return new ChannelsHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChannelsAdapter.ChannelsHolder holder, final int position) {
        Channel channel = channels.get(position);
        Picasso.with(context).load(channel.getThumbnails()).into(holder.imgThumbnail);
        holder.txtTitle.setText(channel.getTitle());
        holder.txtDescription.setText(channel.getDescription());
        holder.llChannelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                channelsView.selectChannelItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public class ChannelsHolder extends RecyclerView.ViewHolder {
        private ImageView imgThumbnail;
        private TextView txtTitle;
        private TextView txtDescription;
        private LinearLayout llChannelItem;

        public ChannelsHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.imageViewThumbnail);
            txtTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            llChannelItem = (LinearLayout) itemView.findViewById(R.id.llChannelItem);
        }
    }
}

package com.vcoders.on_demand_youtube_player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectItem;
import com.vcoders.on_demand_youtube_player.model.PlayList;

import java.util.ArrayList;
import java.util.List;


public class RecentlyPlayedAdapter extends RecyclerView.Adapter<RecentlyPlayedAdapter.RecentlyPlayedHolder> {

    Context context;
    List<PlayList> playLists;
    ISelectItem iSelectItem;

    public RecentlyPlayedAdapter(Context context, List<PlayList> playLists, ISelectItem iSelectItem) {
        this.context = context;
        this.iSelectItem = iSelectItem;
        this.playLists = playLists;
        if (this.playLists == null)
            this.playLists = new ArrayList<>();
    }

    @Override
    public RecentlyPlayedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recently_played, parent, false);
        return new RecentlyPlayedHolder(view);
    }

    public void updateAdapter(List<PlayList> playLists) {
        this.playLists = playLists;
        if (this.playLists == null)
            this.playLists = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecentlyPlayedHolder holder, int position) {
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iSelectItem.onSelectedItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    public class RecentlyPlayedHolder extends RecyclerView.ViewHolder {

        private RelativeLayout llItem;

        public RecentlyPlayedHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.llItem);
        }
    }
}

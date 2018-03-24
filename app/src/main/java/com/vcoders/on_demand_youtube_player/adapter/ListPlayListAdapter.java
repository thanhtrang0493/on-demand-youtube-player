package com.vcoders.on_demand_youtube_player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectPlaylist;
import com.vcoders.on_demand_youtube_player.model.PlayList;

import java.util.ArrayList;
import java.util.List;


public class ListPlayListAdapter extends RecyclerView.Adapter<ListPlayListAdapter.ListPlayListHolder> {

    Context context;
    List<PlayList> playLists;
    ISelectPlaylist listener;

    public ListPlayListAdapter(Context context, List<PlayList> playLists, ISelectPlaylist listener) {
        this.context = context;
        this.playLists = playLists;
        this.listener = listener;
        if (this.playLists == null)
            this.playLists = new ArrayList<>();
    }

    @Override
    public ListPlayListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_play_list, parent, false);
        return new ListPlayListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListPlayListHolder holder, final int position) {
        holder.llItemPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelectPlayList(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    public class ListPlayListHolder extends RecyclerView.ViewHolder {
        private LinearLayout llItemPlaylist;

        public ListPlayListHolder(View itemView) {
            super(itemView);
            llItemPlaylist = (LinearLayout) itemView.findViewById(R.id.llItemPlaylist);
        }
    }
}

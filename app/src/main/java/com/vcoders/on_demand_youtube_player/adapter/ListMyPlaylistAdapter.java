package com.vcoders.on_demand_youtube_player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectItem;
import com.vcoders.on_demand_youtube_player.model.PlayList;

import java.util.ArrayList;
import java.util.List;


public class ListMyPlaylistAdapter extends RecyclerView.Adapter<ListMyPlaylistAdapter.ListMyPlaylistHolder> {

    Context context;
    List<PlayList> playLists;
    ISelectItem listener;

    public ListMyPlaylistAdapter(Context context, List<PlayList> playLists, ISelectItem listener) {
        this.context = context;
        this.listener = listener;
        this.playLists = playLists;
        if (this.playLists == null) {
            this.playLists = new ArrayList<>();
        }
    }

    @Override
    public ListMyPlaylistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_my_playlist, parent, false);
        return new ListMyPlaylistHolder(view);
    }

    @Override
    public void onBindViewHolder(ListMyPlaylistHolder holder, final int position) {
        PlayList playList = playLists.get(position);
        if (playList != null) {
            holder.txtName.setText(playList.getTitle());

            holder.llItemMyPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onSelectedItem(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    public class ListMyPlaylistHolder extends RecyclerView.ViewHolder {
        private LinearLayout llItemMyPlaylist;
        private TextView txtName;

        public ListMyPlaylistHolder(View itemView) {
            super(itemView);
            llItemMyPlaylist=(LinearLayout)itemView.findViewById(R.id.llItemMyPlaylist);
            txtName=(TextView)itemView.findViewById(R.id.txtName);
        }
    }
}

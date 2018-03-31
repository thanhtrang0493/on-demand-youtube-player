package com.vcoders.on_demand_youtube_player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
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

    public void updateAdapter(List<PlayList> playLists) {
        this.playLists = playLists;
        if (this.playLists == null)
            this.playLists = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public ListPlayListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_play_list, parent, false);
        return new ListPlayListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListPlayListHolder holder, final int position) {
        bindData(holder, playLists.get(position));

        holder.llItemPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelectPlayList(position);
            }
        });

        holder.llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMorePlayList(position);
            }
        });
    }

    private void bindData(ListPlayListHolder holder, PlayList playList) {
        Picasso.with(context).load(playList.getThumbnails()).into(holder.imgThumbnails);
        holder.txtItemCount.setText(playList.getVideoCount());
        holder.txtVideoCount.setText(playList.getVideoCount() + " " + context.getResources().getString(R.string.videos));
        holder.txtTitle.setText(playList.getTitle());
        holder.txtType.setText("#" + playList.getLocalized());
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    public class ListPlayListHolder extends RecyclerView.ViewHolder {
        private LinearLayout llItemPlaylist;
        private LinearLayout llMore;
        private ImageView imgThumbnails;
        private TextView txtItemCount;
        private TextView txtTitle;
        private TextView txtType;
        private TextView txtVideoCount;

        public ListPlayListHolder(View itemView) {
            super(itemView);
            llItemPlaylist = (LinearLayout) itemView.findViewById(R.id.llItemPlaylist);
            llMore = (LinearLayout) itemView.findViewById(R.id.llMore);
            imgThumbnails = (ImageView) itemView.findViewById(R.id.imgThumbnails);
            txtItemCount = (TextView) itemView.findViewById(R.id.txtItemCount);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            txtVideoCount = (TextView) itemView.findViewById(R.id.txtVideoCount);
        }
    }
}

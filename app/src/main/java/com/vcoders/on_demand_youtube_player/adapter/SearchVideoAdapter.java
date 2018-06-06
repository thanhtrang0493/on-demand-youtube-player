package com.vcoders.on_demand_youtube_player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectSuggestName;

import java.util.ArrayList;
import java.util.List;


public class SearchVideoAdapter extends RecyclerView.Adapter<SearchVideoAdapter.SearchVideoHolder> {

    Context context;
    List<String> nameList;
    ISelectSuggestName listener;

    public SearchVideoAdapter(Context context, List<String> nameList, ISelectSuggestName listener) {
        this.context = context;
        this.nameList = nameList;
        this.listener = listener;
        if (this.nameList == null)
            this.nameList = new ArrayList<>();
    }

    public void updateAdapter(List<String> listVideo) {
        this.nameList = listVideo;
        if (this.nameList == null)
            this.nameList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public SearchVideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_video, parent, false);
        return new SearchVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchVideoHolder holder, final int position) {
        holder.llSuggestName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelectItemSuggestName(position);
            }
        });

        holder.txtName.setText(nameList.get(position));
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }


    public class SearchVideoHolder extends RecyclerView.ViewHolder {

        private LinearLayout llSuggestName;
        private TextView txtName;

        public SearchVideoHolder(View itemView) {
            super(itemView);
            llSuggestName = (LinearLayout) itemView.findViewById(R.id.llSuggestName);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
        }
    }
}

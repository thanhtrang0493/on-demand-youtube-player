package com.vcoders.on_demand_youtube_player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.model.Topic;

import java.util.ArrayList;
import java.util.List;


public class SelectTopicsAdapter extends RecyclerView.Adapter<SelectTopicsAdapter.SelectTopicsHolder> {

    Context context;
    public List<Topic[]> listTopic;

    public SelectTopicsAdapter(Context context, List<Topic> topics) {
        this.context = context;
        listTopic = groupTopics(topics);
    }

    private List<Topic[]> groupTopics(List<Topic> topics) {
        List<Topic[]> listTopic = new ArrayList<>();
        if (topics != null) {
            Topic[] arrTopics = new Topic[5];
            int i = 0;
            for (Topic item : topics) {
                arrTopics[i] = item;
                if (i < 5) {
                    i++;
                } else {
                    listTopic.add(arrTopics);
                    i = 0;
                    arrTopics = new Topic[5];
                }
            }
            if (i > 0 && arrTopics != null) {
                listTopic.add(arrTopics);
            }
        }
        return listTopic;
    }

    @Override
    public SelectTopicsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_topics, parent, false);
        return new SelectTopicsHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectTopicsHolder holder, int position) {
        Topic[] topics = listTopic.get(position);
        if (topics != null) {
            for (int i = 0; i < topics.length; i++) {
                Topic topic = topics[i];
                holder.listTextViewTopic.get(i).setText(topic.getName());
                holder.listTextViewTopic.get(i).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listTopic.size();
    }

    public class SelectTopicsHolder extends RecyclerView.ViewHolder {
        private TextView txtTopic1;
        private TextView txtTopic2;
        private TextView txtTopic3;
        private TextView txtTopic4;
        private TextView txtTopic5;
        private List<TextView> listTextViewTopic = new ArrayList<>();

        public SelectTopicsHolder(View itemView) {
            super(itemView);
            txtTopic1 = (TextView) itemView.findViewById(R.id.txtTopic1);
            txtTopic2 = (TextView) itemView.findViewById(R.id.txtTopic2);
            txtTopic3 = (TextView) itemView.findViewById(R.id.txtTopic3);
            txtTopic4 = (TextView) itemView.findViewById(R.id.txtTopic4);
            txtTopic5 = (TextView) itemView.findViewById(R.id.txtTopic5);

            txtTopic1.setVisibility(View.GONE);
            txtTopic2.setVisibility(View.GONE);
            txtTopic3.setVisibility(View.GONE);
            txtTopic4.setVisibility(View.GONE);
            txtTopic5.setVisibility(View.GONE);

            listTextViewTopic.add(txtTopic1);
            listTextViewTopic.add(txtTopic2);
            listTextViewTopic.add(txtTopic3);
            listTextViewTopic.add(txtTopic4);
            listTextViewTopic.add(txtTopic5);
        }
    }
}

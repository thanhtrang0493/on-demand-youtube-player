package com.vcoders.on_demand_youtube_player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.features.home.HomeView;
import com.vcoders.on_demand_youtube_player.interfaces.IChangeTopic;
import com.vcoders.on_demand_youtube_player.model.Topic;

import java.util.ArrayList;
import java.util.List;


public class ListTopicAdapter extends RecyclerView.Adapter<ListTopicAdapter.ListTopicHolder> {

    Context context;
    List<Topic> topics;
    int positionSelect = 0;
    IChangeTopic listener;

    public ListTopicAdapter(Context context, List<Topic> topics, IChangeTopic listener) {
        this.context = context;
        this.listener = listener;
        this.topics = topics;
        if (this.topics == null)
            this.topics = new ArrayList<>();
    }

    public void updateAdapter(List<Topic> topics){
        this.topics = topics;
        if (this.topics == null)
            this.topics = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public ListTopicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_topic, parent, false);
        return new ListTopicHolder(view);
    }

    @Override
    public void onBindViewHolder(ListTopicHolder holder, final int position) {
        final Topic topic = topics.get(position);
        if (topic != null) {
            holder.txtTopic.setText(topic.getName());
            if (topic.isSelect()) {
                selectTopic(holder.txtTopic);
            } else {
                unSelectTopic(holder.txtTopic);
            }

            holder.txtTopic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!topic.isSelect()) {
                        // unselect topic
                        Topic topicOld = topics.get(positionSelect);
                        topicOld.setSelect(false);
                        topics.set(positionSelect, topicOld);

                        topic.setSelect(true);
                        positionSelect = position;
                        topics.set(position, topic);
                        notifyDataSetChanged();

                        listener.changeTopic(positionSelect);
                    }
                }
            });
        }
    }

    private void selectTopic(TextView textView) {
        textView.setBackgroundResource(R.drawable.border_corner_gradient_blue);
        textView.setTextColor(context.getResources().getColor(R.color.white));
    }

    private void unSelectTopic(TextView textView) {
        textView.setBackgroundResource(R.drawable.border_gray_bg_transparent);
        textView.setTextColor(context.getResources().getColor(R.color.gray_unselected));
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class ListTopicHolder extends RecyclerView.ViewHolder {
        private TextView txtTopic;

        public ListTopicHolder(View itemView) {
            super(itemView);
            txtTopic = (TextView) itemView.findViewById(R.id.txtTopic);
        }
    }
}

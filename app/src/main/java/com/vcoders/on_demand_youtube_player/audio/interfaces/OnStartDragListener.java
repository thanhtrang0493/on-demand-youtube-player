package com.vcoders.on_demand_youtube_player.audio.interfaces;

import android.support.v7.widget.RecyclerView;

/**
 * Listener for manual initiation of a drag.
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-May-15
 */

public interface OnStartDragListener
{
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}

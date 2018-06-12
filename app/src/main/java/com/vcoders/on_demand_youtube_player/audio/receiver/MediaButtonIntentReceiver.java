package com.vcoders.on_demand_youtube_player.audio.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vcoders.on_demand_youtube_player.utils.LogHelper;

public class MediaButtonIntentReceiver extends BroadcastReceiver
{
    private static final String TAG = LogHelper.makeLogTag(MediaButtonIntentReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent)
    {
        LogHelper.e(TAG, "onReceive MediaButtonIntentReceiver");
    }
}

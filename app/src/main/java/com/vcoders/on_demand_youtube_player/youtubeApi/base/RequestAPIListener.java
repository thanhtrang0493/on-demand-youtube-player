package com.vcoders.on_demand_youtube_player.youtubeApi.base;


import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;

public interface RequestAPIListener<Type> {
    void onResponse(ResponseAPIListener<Type> response);
}


//    insert video into a playlist with youtube api v3
//
//    POST https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&key={YOUR_API_KEY}
//
//        Content-Type:  application/json
//        Authorization:  Bearer ya29.1.AADtN_WT2TRZzH1t86nVlX26z9WPp-gnDTxVHGvdQ6xx0vyTzmkYeXkLdJerwllLzF_a
//        X-JavaScript-User-Agent:  Google APIs Explorer
//
//        "snippet": {
//        "playlistId": "PL8hD12HFC-nuswc21_e64aPAy9B25sEH7",
//        "resourceId": {
//        "kind": "youtube#video",
//        "videoId": "KMGuyGY5gvY"
//        }
//        }
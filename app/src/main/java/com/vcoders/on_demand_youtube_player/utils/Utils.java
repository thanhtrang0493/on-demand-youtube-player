package com.vcoders.on_demand_youtube_player.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

import com.vcoders.on_demand_youtube_player.audio.youtubeExtractor.YtFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.android.exoplayer2.mediacodec.MediaCodecInfo.TAG;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_ITAG_140;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_ITAG_141;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_ITAG_17;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_ITAG_171;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_ITAG_18;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_ITAG_22;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_ITAG_249;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_ITAG_250;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_ITAG_251;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_ITAG_36;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_ITAG_43;

public class Utils {
    private static final Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
    }

    private Utils() {
    }

    public void showError(Context context, String error) {

    }

    public void showLoading(Context context, Boolean isShow) {

    }

    public void changeActivity(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void changeActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public int calculateDays(String date) {
        int days = 0;

        SimpleDateFormat formatTo = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        formatTo.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (date != null && formatTo != null) {
            try {
                Date toDate = formatTo.parse(date);
                Date currentTime = Calendar.getInstance().getTime();
                long diff = currentTime.getTime() - toDate.getTime();
                days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            } catch (ParseException e) {

            }
        }
        return days;
    }

    public String getCurrentDate() {
        Date currentTime = Calendar.getInstance().getTime();
        return currentTime.toString();
    }

    public String convertIntToString(int data) {
        String value = String.valueOf(data);
        return value;
    }

    public String includeSpaceIntoString(String value) {
        String data = "";
        if (value != null) {
            data = value.replaceAll(" ", "%20");
        }
        return data;
    }

    private final NavigableMap<Long, String> suffixes = new TreeMap<>();
    public String formatLong(long value)
    {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatLong(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatLong(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    private long segmentToLong(String[] segments)
    {
        long number = 0;
        int count = segments.length - 1;
        for (String segment : segments) {
            try {
                number += Integer.parseInt(segment) * Math.pow(10, 3 * count--);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }

        return number;
    }

    public String formatViewCount(String viewCounts)
    {
        if(viewCounts!=null) {
            String[] split = viewCounts.split(" ");
            String[] segments = split[0].split(",");

            return formatLong(segmentToLong(segments)) + " " + split[1];
        }

        return "";
    }

    public static boolean validateUrl(String url)
    {
        // https://r8---sn-3u-bh2ee.googlevideo.com/videoplayback
        return url.contains(".googlevideo.com/videoplayback");
    }

    /**
     * Get the best available audio stream
     *
     * @param ytFiles Array of available streams
     * @return Audio stream with highest bitrate
     */
    public static YtFile getBestStream(SparseArray<YtFile> ytFiles)
    {
//        Log.e(TAG, "ytFiles: " + ytFiles);
        if (ytFiles.get(YOUTUBE_ITAG_141) != null) {
            LogHelper.e(TAG, " gets YOUTUBE_ITAG_141");
            return ytFiles.get(YOUTUBE_ITAG_141);
        } else if (ytFiles.get(YOUTUBE_ITAG_140) != null) {
            LogHelper.e(TAG, " gets YOUTUBE_ITAG_140");
            return ytFiles.get(YOUTUBE_ITAG_140);
        } else if (ytFiles.get(YOUTUBE_ITAG_251) != null) {
            LogHelper.e(TAG, " gets YOUTUBE_ITAG_251");
            return ytFiles.get(YOUTUBE_ITAG_251);
        } else if (ytFiles.get(YOUTUBE_ITAG_250) != null) {
            LogHelper.e(TAG, " gets YOUTUBE_ITAG_250");
            return ytFiles.get(YOUTUBE_ITAG_250);
        } else if (ytFiles.get(YOUTUBE_ITAG_249) != null) {
            LogHelper.e(TAG, " gets YOUTUBE_ITAG_249");
            return ytFiles.get(YOUTUBE_ITAG_249);
        } else if (ytFiles.get(YOUTUBE_ITAG_171) != null) {
            LogHelper.e(TAG, " gets YOUTUBE_ITAG_171");
            return ytFiles.get(YOUTUBE_ITAG_171);
        } else if (ytFiles.get(YOUTUBE_ITAG_18) != null) {
            LogHelper.e(TAG, " gets YOUTUBE_ITAG_18");
            return ytFiles.get(YOUTUBE_ITAG_18);
        } else if (ytFiles.get(YOUTUBE_ITAG_22) != null) {
            LogHelper.e(TAG, " gets YOUTUBE_ITAG_22");
            return ytFiles.get(YOUTUBE_ITAG_22);
        } else if (ytFiles.get(YOUTUBE_ITAG_43) != null) {
            LogHelper.e(TAG, " gets YOUTUBE_ITAG_43");
            return ytFiles.get(YOUTUBE_ITAG_43);
        } else if (ytFiles.get(YOUTUBE_ITAG_36) != null) {
            LogHelper.e(TAG, " gets YOUTUBE_ITAG_36");
            return ytFiles.get(YOUTUBE_ITAG_36);
        }

        LogHelper.e(TAG, " gets YOUTUBE_ITAG_17");
        return ytFiles.get(YOUTUBE_ITAG_17);
    }
}

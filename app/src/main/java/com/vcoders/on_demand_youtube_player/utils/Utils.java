package com.vcoders.on_demand_youtube_player.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        try {
            Date toDate = formatTo.parse(date);
            Date currentTime = Calendar.getInstance().getTime();
            long diff = currentTime.getTime() - toDate.getTime();
            days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {

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

}

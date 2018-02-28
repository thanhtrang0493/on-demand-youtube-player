package com.vcoders.on_demand_youtube_player.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

}

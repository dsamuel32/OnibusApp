package br.com.onibusapp.onibusapp.utils;

import android.content.Context;

/**
 * Created by diego on 26/07/2018.
 */

public final class StringUtils {

    private static StringUtils instance;
    private Context context;

    private StringUtils(Context context) {
        this.context = context;
    }

    public static StringUtils getInstance(Context context) {
        if (instance == null) {
            instance = new StringUtils(context);
        }
        return instance;
    }

    public String getString(int string) {
        return context.getString(string);
    }

}

package com.obzen.tagworks.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.obzen.tagworks.constants.TagWorksParams;

public class PreferencesUtil {

    private static final String PREFERENCES_TAG = "com.obzen.tagworks";
    private static final String DEFAULT_VALUE_STRING = "";
    private static final boolean DEFAULT_VALUE_BOOLEAN = false;
    private static final int DEFAULT_VALUE_INT = -1;
    private static final long DEFAULT_VALUE_LONG = -1L;
    private static final float DEFAULT_VALUE_FLOAT = -1F;

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_TAG, Context.MODE_PRIVATE);
    }
    public static void setString(Context context, TagWorksParams key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key.getValue(), value);
        editor.commit();
    }
    public static void setBoolean(Context context, TagWorksParams key, boolean value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key.getValue(), value);
        editor.commit();
    }
    public static void setInt(Context context, TagWorksParams key, int value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key.getValue(), value);
        editor.commit();
    }
    public static void setLong(Context context, TagWorksParams key, long value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key.getValue(), value);
        editor.commit();
    }
    public static void setFloat(Context context, TagWorksParams key, float value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key.getValue(), value);
        editor.commit();
    }
    public static String getString(Context context, TagWorksParams key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key.getValue(), DEFAULT_VALUE_STRING);
        return value;
    }
    public static boolean getBoolean(Context context, TagWorksParams key) {
        SharedPreferences prefs = getPreferences(context);
        boolean value = prefs.getBoolean(key.getValue(), DEFAULT_VALUE_BOOLEAN);
        return value;
    }
    public static int getInt(Context context, TagWorksParams key) {
        SharedPreferences prefs = getPreferences(context);
        int value = prefs.getInt(key.getValue(), DEFAULT_VALUE_INT);
        return value;
    }
    public static long getLong(Context context, TagWorksParams key) {
        SharedPreferences prefs = getPreferences(context);
        long value = prefs.getLong(key.getValue(), DEFAULT_VALUE_LONG);
        return value;
    }
    public static float getFloat(Context context, TagWorksParams key) {
        SharedPreferences prefs = getPreferences(context);
        float value = prefs.getFloat(key.getValue(), DEFAULT_VALUE_FLOAT);
        return value;
    }
    public static void removeKey(Context context, TagWorksParams key) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(key.getValue());
        edit.commit();
    }
    public static void clear(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }
}

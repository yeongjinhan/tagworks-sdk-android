//
//  PreferencesUtil
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.obzen.tagworks.constants.TagWorksKey;

/**
 * TagWorks SharedPreferences 관리 클래스입니다.
 * @author hanyj
 * @version v1.0.0 2023.08.10
 */
public class PreferencesUtil {
    private static final String PREFERENCES_TAG = "com.obzen.tagworks";
    private static final String DEFAULT_VALUE_STRING = "";
    private static final boolean DEFAULT_VALUE_BOOLEAN = false;
    private static final int DEFAULT_VALUE_INT = -1;
    private static final long DEFAULT_VALUE_LONG = -1L;
    private static final float DEFAULT_VALUE_FLOAT = -1F;

    private static synchronized SharedPreferences getPreferences(@NonNull Context context) {
        return context.getSharedPreferences(PREFERENCES_TAG, Context.MODE_PRIVATE);
    }
    public static void setString(Context context, @NonNull TagWorksKey key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key.getValue(), value);
        editor.commit();
    }
    public static void setBoolean(Context context, @NonNull TagWorksKey key, boolean value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key.getValue(), value);
        editor.commit();
    }
    public static void setInt(Context context, @NonNull TagWorksKey key, int value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key.getValue(), value);
        editor.commit();
    }
    public static void setLong(Context context, @NonNull TagWorksKey key, long value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key.getValue(), value);
        editor.commit();
    }
    public static void setFloat(Context context, @NonNull TagWorksKey key, float value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key.getValue(), value);
        editor.commit();
    }
    public static String getString(Context context, @NonNull TagWorksKey key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key.getValue(), DEFAULT_VALUE_STRING);
        return value;
    }
    public static boolean getBoolean(Context context, @NonNull TagWorksKey key) {
        SharedPreferences prefs = getPreferences(context);
        boolean value = prefs.getBoolean(key.getValue(), DEFAULT_VALUE_BOOLEAN);
        return value;
    }
    public static int getInt(Context context, @NonNull TagWorksKey key) {
        SharedPreferences prefs = getPreferences(context);
        int value = prefs.getInt(key.getValue(), DEFAULT_VALUE_INT);
        return value;
    }
    public static long getLong(Context context, @NonNull TagWorksKey key) {
        SharedPreferences prefs = getPreferences(context);
        long value = prefs.getLong(key.getValue(), DEFAULT_VALUE_LONG);
        return value;
    }
    public static float getFloat(Context context, @NonNull TagWorksKey key) {
        SharedPreferences prefs = getPreferences(context);
        float value = prefs.getFloat(key.getValue(), DEFAULT_VALUE_FLOAT);
        return value;
    }
    public static void removeKey(Context context, @NonNull TagWorksKey key) {
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

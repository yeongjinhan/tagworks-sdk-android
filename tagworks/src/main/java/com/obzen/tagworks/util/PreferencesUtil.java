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

/**
 * The type Preferences util.
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

    /**
     * Sets string.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void setString(Context context, @NonNull String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Sets boolean.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void setBoolean(Context context, @NonNull String key, boolean value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * Sets int.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void setInt(Context context, @NonNull String key, int value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Sets long.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void setLong(Context context, @NonNull String key, long value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * Sets float.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void setFloat(Context context, @NonNull String key, float value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * Gets string.
     *
     * @param context the context
     * @param key     the key
     * @return the string
     */
    public static String getString(Context context, @NonNull String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, DEFAULT_VALUE_STRING);
        return value;
    }

    /**
     * Gets boolean.
     *
     * @param context the context
     * @param key     the key
     * @return the boolean
     */
    public static boolean getBoolean(Context context, @NonNull String key) {
        SharedPreferences prefs = getPreferences(context);
        boolean value = prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN);
        return value;
    }

    /**
     * Gets int.
     *
     * @param context the context
     * @param key     the key
     * @return the int
     */
    public static int getInt(Context context, @NonNull String key) {
        SharedPreferences prefs = getPreferences(context);
        int value = prefs.getInt(key, DEFAULT_VALUE_INT);
        return value;
    }

    /**
     * Gets long.
     *
     * @param context the context
     * @param key     the key
     * @return the long
     */
    public static long getLong(Context context, @NonNull String key) {
        SharedPreferences prefs = getPreferences(context);
        long value = prefs.getLong(key, DEFAULT_VALUE_LONG);
        return value;
    }

    /**
     * Gets float.
     *
     * @param context the context
     * @param key     the key
     * @return the float
     */
    public static float getFloat(Context context, @NonNull String key) {
        SharedPreferences prefs = getPreferences(context);
        float value = prefs.getFloat(key, DEFAULT_VALUE_FLOAT);
        return value;
    }

    /**
     * Remove key.
     *
     * @param context the context
     * @param key     the key
     */
    public static void removeKey(Context context, @NonNull String key) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(key);
        edit.commit();
    }

    /**
     * Clear.
     *
     * @param context the context
     */
    public static void clear(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }
}

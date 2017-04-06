package com.example.kangjisung.likeroom.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceManager {
    private static String stringErrorMessage = "error";
    private static int intErrorMessage = 0;
    private static boolean booleanErrorMessage = false;
    private static float floatErrorMessage = 0.0f;

    public String getString(String key, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key, stringErrorMessage);
    }

    public String getStringByPrefName(String prefName, String key, Context context) {
        SharedPreferences pref = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        return pref.getString(key, stringErrorMessage);
    }
    public String getString(String key, String stringErrorMessage, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key, stringErrorMessage);
    }
    public int getInt(String key, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getInt(key, intErrorMessage);
    }

    public int getInt(String key, int defValue, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getInt(key, defValue);
    }

    public int getInt(String prefName, String key, Context context) {
        SharedPreferences pref = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        return pref.getInt(key, intErrorMessage);
    }

    public boolean getBoolean(String key, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getBoolean(key, booleanErrorMessage);
    }

    public boolean getBoolean(String prefName, String key, Context context) {
        SharedPreferences pref = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        return pref.getBoolean(key, booleanErrorMessage);
    }

    public float getFloat(String key, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getFloat(key, floatErrorMessage);
    }

    public float getFloat(String prefName, String key, Context context) {
        SharedPreferences pref = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        return pref.getFloat(key, floatErrorMessage);
    }

    // 값 저장하기
    public void putString(String key, String value, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putString(String prefName, String key, String value, Context context) {
        SharedPreferences pref = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putBoolean(String prefName, String key, boolean value, Context context) {
        SharedPreferences pref = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putFloat(String key, float value, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public void putFloat(String prefName, String key, float value, Context context) {
        SharedPreferences pref = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, value);
        editor.commit();
    }
    public void putInt(String key, int value, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putInt(String prefName, String key, int value, Context context) {
        SharedPreferences pref = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    // 값(Key Data) 삭제하기
    public void removePreferences(String key, Context context) {
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    public void removePreferencesDefault(String key, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    public void removeAllPreferences(Context context) {
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }


    // 값(ALL Data) 삭제하기
    public void removeAllPreferencesDefault(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}

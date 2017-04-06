package com.example.kangjisung.likeroom.Util;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.util.TypedValue;

import com.example.kangjisung.likeroom.R;

public class ColorTheme {
    public static int theme = 0;

    public static void initTheme(Context context)
    {
        SharedPreferenceManager mSharedPreferenceManager = new SharedPreferenceManager();
        theme = mSharedPreferenceManager.getInt("set_theme", context);
        if(theme == 0){
            theme = R.style.LikeRoomTheme_BreadTheme;
        }
    }

    public static int getThemeColorRGB(Context context, @AttrRes int attrId) {
        TypedValue styleValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attrId, styleValue, true) == true) {
            return styleValue.data;
        }
        return R.color.transparent;
    }

    public static int getTheme()
    {
        return theme;
    }
}
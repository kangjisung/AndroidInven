package com.example.kangjisung.likeroom.Util;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.util.TypedValue;

import com.example.kangjisung.likeroom.R;

public class ColorTheme {
    public static int basicTheme=2;
    public static int getThemeColorRGB(Context context, @AttrRes int attrId) {
        TypedValue styleValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attrId, styleValue, true) == true) {
            return styleValue.data;
        }
        return R.color.transparent;
    }

    public static int getTheme(int theme)
    {
        switch(theme) {
            case 1:
                return R.style.LikeRoomTheme_BreadTheme;
            case 2:
                return R.style.LikeRoomTheme_StrawBerryTheme;
            case 3:
                return R.style.LikeRoomTheme_OceanTheme;
            default:
                return R.style.LikeRoomTheme_BreadTheme;
        }
    }

    public static void Initialize(Context context)
    {
    }
}
package com.example.kangjisung.likeroom.CustomClass;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.util.TypedValue;

public class ColorTheme
{
    public static int getThemeColorRGB(Context context, @AttrRes int attrId)
    {
        TypedValue styleValue = new TypedValue();
        if(context.getTheme().resolveAttribute(attrId, styleValue, true) == true) {
            return styleValue.data;
        }
        return android.R.color.transparent;
    }

    public static void Initialize(Context context)
    {
    }
}
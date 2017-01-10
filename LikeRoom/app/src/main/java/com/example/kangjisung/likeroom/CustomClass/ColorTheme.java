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
        /*
        LayerDrawable layers = (LayerDrawable)ContextCompat.getDrawable(context, R.drawable.dialog_emblem);
        GradientDrawable shape = (GradientDrawable)layers.findDrawableByLayerId(R.id.drawable_dialog_emblem);
        shape.setColorFilter(getThemeColorRGB(context, R.attr.theme_color_D3), PorterDuff.Mode.SRC_IN);
        boolean p = layers.setDrawableByLayerId(R.id.drawable_dialog_emblem, shape);

        int t = 0;
        */

        /*
        tabLayout.getTabAt(0).getIcon().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D3), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);
        */

        // 탭 아이콘 색상 변경
        /*
        LinearLayout view;
        view = (LinearLayout)tabLayout.getTabAt(0).getCustomView();
        view.findViewById(R.id.icon).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D3), PorterDuff.Mode.SRC_IN);
        view = (LinearLayout)tabLayout.getTabAt(1).getCustomView();
        view.findViewById(R.id.icon).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);
        view = (LinearLayout)tabLayout.getTabAt(2).getCustomView();
        view.findViewById(R.id.icon).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);
        */
    }
}
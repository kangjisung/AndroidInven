package com.example.kangjisung.likeroom.Util;

import android.view.View;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;

public class LayoutManager {
    public static final int MENU_USER_MODIFY = 0;
    public static final int MENU_USER_DELETE = 1;
    public static final int MENU_PRODUCT_MODIFY = 2;
    public static final int MENU_PRODUCT_DELETE = 3;
    public static final int MENU_NOTICE_DELETE = 4;

    public static void setActivityTitle(View parent, boolean isBack, boolean isClose, int color, String title)
    {
        if(isBack == false) {
            parent.findViewById(R.id.inc_btn_back).setVisibility(View.INVISIBLE);
        }
        if(isClose == false){
            parent.findViewById(R.id.inc_btn_close).setVisibility(View.INVISIBLE);
        }
        if(color != 0){
            parent.setBackgroundColor(ColorTheme.getThemeColorRGB(parent.getContext(), color));
        }
        ((TextView) parent.findViewById(R.id.inc_tv_title)).setText(title);
    }

    public static void setActivityTitle(View parent, boolean isBack, boolean isClose, String title)
    {
        if(isBack == false) {
            parent.findViewById(R.id.inc_btn_back).setVisibility(View.INVISIBLE);
        }
        if(isClose == false){
            parent.findViewById(R.id.inc_btn_close).setVisibility(View.INVISIBLE);
        }
        ((TextView) parent.findViewById(R.id.inc_tv_title)).setText(title);
    }

    public static void setDialogTitle(View parent, boolean isBack, boolean isOk, String title)
    {
        if(isBack == false) {
            parent.findViewById(R.id.inc_btn_back).setVisibility(View.INVISIBLE);
        }
        if(isOk == false){
            parent.findViewById(R.id.inc_btn_ok).setVisibility(View.INVISIBLE);
        }
        ((TextView) parent.findViewById(R.id.inc_tv_title)).setText(title);
    }
}
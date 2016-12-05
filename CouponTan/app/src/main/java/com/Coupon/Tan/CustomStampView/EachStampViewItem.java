package com.Coupon.Tan.CustomStampView;

import android.graphics.drawable.Drawable;

/**
 * Created by stories2 on 2016. 11. 28..
 */

public class EachStampViewItem {
    Drawable eachStampIcon;
    String eachStampSubTitle;

    public void SetEachStampIcon(Drawable eachStampIcon) {
        this.eachStampIcon = eachStampIcon;
    }

    public void SetEachStampSubTitle(String eachStampSubTitle) {
        this.eachStampSubTitle = eachStampSubTitle;
    }

    public Drawable GetEachStampIcon() {
        return eachStampIcon;
    }

    public String GetEachStampSubTitle() {
        return eachStampSubTitle;
    }
}

package com.teamdk.android.bakery.utility;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Ahn on 2016-12-29.
 */

public class NoScrollViewPager extends ViewPager
{
    private boolean enabled;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (this.enabled) {
                return super.onTouchEvent(event);
            }
        }
        catch (Exception e) {}

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled() { //이 메소드를 이용해서 스크롤을 풀어주고
        this.enabled = true;
    }

    public void setPagingDisabled() { //이 메소드를 이용해서 스크롤을 막아줍니다.
        this.enabled = false;
    }
}
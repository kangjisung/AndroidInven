package com.example.kangjisung.likeroom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.kangjisung.likeroom.FragmentInfo.FragmentInfoMain;
import com.example.kangjisung.likeroom.FragmentNotice.FragmentNoticeMain;
import com.example.kangjisung.likeroom.FragmentStamp.FragmentStampMain;

//ActivityMenu와 연결되어 있으며 실제 어플에서 맨 아래 하단 3개의 아이콘(스탬프,공지사항,매장정보)를
//각각 눌렀을 때 창을 이동시켜주는 부분을 담당한다.

public class ActivityMenuPagerAdapter extends FragmentStatePagerAdapter
{
    int mNumOfTabs;
    String[] selectedShopInfoData;

    public ActivityMenuPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public ActivityMenuPagerAdapter(FragmentManager fm, int NumOfTabs, String[] selectedShopInfoData) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.selectedShopInfoData = selectedShopInfoData;
    }

    @Override
    public Fragment getItem(int position)
    {
        Bundle dataTransferManager = new Bundle();
        dataTransferManager.putStringArray("shopInfoData", selectedShopInfoData);
        switch (position) {
            case 0:
                FragmentStampMain shopStampManager = new FragmentStampMain();
                shopStampManager.setArguments(dataTransferManager);
                return shopStampManager;
            case 1:
                FragmentNoticeMain shopNoticeManager = new FragmentNoticeMain();
                shopNoticeManager.setArguments(dataTransferManager);
                return shopNoticeManager;
            case 2:
                FragmentInfoMain shopInfoManager = new FragmentInfoMain();
                shopInfoManager.setArguments(dataTransferManager);
                return shopInfoManager;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
package com.example.kangjisung.likeroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kangjisung.likeroom.FragmentUser.UserStampDialogPagerAdapter;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.NoScrollViewPager;

public class ActivityStoreAdd extends AppCompatActivity {
    NoScrollViewPager viewPager;
    ActivityStoreAddPagerAdapter pagerAdapter;
    EditText StoreName, StoreAddress, StorePhone;
    Button StoreAddBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_store_add);

        viewPager = (NoScrollViewPager)findViewById(R.id.viewPager);

        pagerAdapter = new ActivityStoreAddPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPagingDisabled();
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void onBackPressed()
    {
        if(viewPager.getCurrentItem() > 0){
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
        else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("앱을 종료하시겠습니까?");
            dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }
        //super.onBackPressed();
    }
}

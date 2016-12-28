package com.example.kangjisung.likeroom.inventory.statistics.ChangeStat;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.kangjisung.likeroom.R;

public class StatActivity extends AppCompatActivity {
    ViewPager viewPager;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        viewPager = (ViewPager)findViewById(R.id.pager);

        customAdapter = new CustomAdapter(getSupportFragmentManager());

        viewPager.setAdapter(customAdapter);
    }

    public void mOnClick(View v){
        switch(v.getId()){
            case R.id.stat_menu1:viewPager.setCurrentItem(0,true);break;
            case R.id.stat_menu2:viewPager.setCurrentItem(3,true);break;
        }
    }
}

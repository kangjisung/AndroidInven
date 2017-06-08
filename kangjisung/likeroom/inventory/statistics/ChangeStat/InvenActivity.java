package com.example.kangjisung.likeroom.inventory.statistics.ChangeStat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.inventory.calc;

public class InvenActivity extends AppCompatActivity {
    ViewPager viewPager;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inven);

        Intent mil = getIntent(); ////user클래스에서 눌렀던 사용자의 이름을 가져옴
        final String name = mil.getExtras().getString("name");  //가져온 사용자의 이름을 넣음
        calc c=calc.getInstance();
        c.setName(name);
        c.InitCalc();

        viewPager = (ViewPager)findViewById(R.id.pager);

        customAdapter = new CustomAdapter(getSupportFragmentManager());

        viewPager.setAdapter(customAdapter);
    }
}
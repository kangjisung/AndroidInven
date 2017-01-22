package com.example.kangjisung.likeroom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.kangjisung.likeroom.FragmentProduct.ProductListItem;
import com.example.kangjisung.likeroom.FragmentProduct.ProductObjManager;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.Util.ColorTheme;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private Handler mHandler;
    private Runnable mRunnable;
    static DatabaseHelper databaseHelperTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_main);
        databaseHelperTest = new DatabaseHelper(getApplicationContext(), ClientDataBase.testDatabaseName);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ActivityMenu.class);
                startActivity(intent);

                finish();
            }
        };

        //////////////////////////////////////////요기서, 제품 정보 배열 추가.
        ProductObjManager.add(new ProductListItem("소보로빵", true, new Date(2011,10,20), new Date(2000,2,3), new Date(2010,12,30), 100, 200));
        ProductObjManager.add(new ProductListItem("정성욱", false, new Date(2012,10,20), new Date(2020,2,3), new Date(2002,12,30), 100, 200));
        ProductObjManager.add(new ProductListItem("오연오", false, new Date(2013,10,20), new Date(2030,2,3), new Date(2004,12,30), 100, 200));
        ProductObjManager.add(new ProductListItem("박정현", true, new Date(2014,10,20), new Date(2040,2,3), new Date(2020,12,30), 100, 200));
        ProductObjManager.add(new ProductListItem("김진성", false, new Date(2012,9,20), new Date(2050,2,3), new Date(2015,12,30), 100, 200));
        ProductObjManager.context=getApplicationContext();

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000);
    }

    protected void onDestroy() {
        mHandler.removeCallbacks(mRunnable);

        super.onDestroy();
    }
}
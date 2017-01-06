package com.example.kangjisung.likeroom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.kangjisung.likeroom.Util.ColorTheme;

public class MainActivity extends AppCompatActivity
{
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(R.style.LikeRoomTheme_BreadTheme);
        setContentView(R.layout.activity_main);
        ColorTheme.Initialize(this);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ActivityMenu.class);
                startActivity(intent);

                finish();
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000);
    }

    protected void onDestroy() {
        mHandler.removeCallbacks(mRunnable);

        super.onDestroy();
    }
}
package com.Coupon.Tan.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.Coupon.Tan.R;
import com.Coupon.Tan.UserSettingManager.UserLoginSection;

/**
 * Created by stories2 on 2016. 12. 1..
 */

public class CouponTanSplashManager extends Activity{
    ImageView startUpImageView;
    Animation splashScreenFadeInAnimation, splashScreenFadeOutAnimation;
    String logCatTag;
    boolean fadeOutProcessed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_startup_screen);

        startUpImageView = (ImageView)findViewById(R.id.startUpImageView);

        splashScreenFadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        splashScreenFadeOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        startUpImageView.setAnimation(splashScreenFadeInAnimation);


        logCatTag = getString(R.string.app_name);

        splashScreenFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(CouponTanSplashManager.this, UserLoginSection.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        splashScreenFadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(!fadeOutProcessed) {
                    startUpImageView.setAnimation(splashScreenFadeOutAnimation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}

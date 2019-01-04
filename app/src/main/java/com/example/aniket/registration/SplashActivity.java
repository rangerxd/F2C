package com.example.aniket.registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    //TextView txtv_splash;
ImageView img_splash;
    Animation animation;

String usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        img_splash=(ImageView) findViewById(R.id.img_splash);
        //txtv_splash=(TextView) findViewById(R.id.txtv_splash);
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        animation= AnimationUtils.loadAnimation(this,R.anim.from_top);
        img_splash.setAnimation(animation);
        //txtv_splash.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
                usertype=sharedPreferences.getString("usertype","0");
                if(sharedPreferences.getBoolean("IS_LOGIN",false) &&usertype.equals("farmer"))
                {
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                }
                else if(sharedPreferences.getBoolean("IS_LOGIN",false) &&usertype.equals("vendor"))
                {
                    startActivity(new Intent(SplashActivity.this,timeline.class));
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}

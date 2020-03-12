package com.plurals.android.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.plurals.android.R;
import com.plurals.android.Utility.SharedPref;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    SharedPref sharedPref = SharedPref.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(mUpdateTimeTask, 2000);
    }
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (sharedPref.isLogin(SplashActivity.this)) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}

package com.pavelekozhevnikov.homework1;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class BaseThemeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(App.getInstance().isDarkTheme){
           setTheme(R.style.AppDarkTheme);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        int countOfFragmentInManager = getSupportFragmentManager().getBackStackEntryCount();
        if(countOfFragmentInManager>0){
            getSupportFragmentManager().popBackStack();
        }
    }

    protected void toggleTheme(){
        App.getInstance().toggleTheme();
        if(App.getInstance().isDarkTheme){
            setTheme(R.style.AppDarkTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        recreate();
    }
}
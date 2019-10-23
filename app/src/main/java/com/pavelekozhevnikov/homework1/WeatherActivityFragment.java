package com.pavelekozhevnikov.homework1;

import android.content.res.Configuration;
import android.os.Bundle;

import com.pavelekozhevnikov.homework1.Fragment.WeatherFragment;

public class WeatherActivityFragment extends BaseThemeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather_fragment);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Если устройство перевернули в альбомную ориентацию, то надо эту activity закрыть
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // Если эта activity запускается первый раз (с каждым новым гербом первый раз)
            // то перенаправим параметр фрагменту
            WeatherFragment details = new WeatherFragment();
            details.setArguments(getIntent().getExtras());
            // Добавим фрагмент на activity
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.weather_container, details).commit();
        }

    }


}

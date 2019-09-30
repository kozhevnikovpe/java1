package com.pavelekozhevnikov.homework1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        String city = getIntent().getExtras().getString(String.valueOf(R.string.cityParam));
        boolean needHumidity = getIntent().getExtras().getBoolean(String.valueOf(R.string.humidityParam));
        boolean needWind = getIntent().getExtras().getBoolean(String.valueOf(R.string.windParam));
        TextView cityLabel = findViewById(R.id.cityLabel);
        cityLabel.setText( getResources().getString(R.string.cityLabel)+" "+city);
        TextView temperatureLabel = findViewById(R.id.temperatureLabel);
        temperatureLabel.setText( getResources().getString(R.string.temperatureLabel)+" "+ getResources().getString(R.string.temperatureValue));
        if(needHumidity) {
            TextView humidityLabel = findViewById(R.id.humidityLabel);
            humidityLabel.setText( getResources().getString(R.string.humidityLabel)+" "+ getResources().getString(R.string.humidityValue));
            humidityLabel.setVisibility(View.VISIBLE);
        }
        if(needWind) {
            TextView windLabel = findViewById(R.id.windLabel);
            windLabel.setText( getResources().getString(R.string.windLabel)+" "+ getResources().getString(R.string.windValue));
            windLabel.setVisibility(View.VISIBLE);
        }
    }
}

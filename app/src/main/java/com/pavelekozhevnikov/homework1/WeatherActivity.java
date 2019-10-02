package com.pavelekozhevnikov.homework1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        boolean needHumidity = Objects.requireNonNull(getIntent().getExtras()).getBoolean(String.valueOf(R.string.humidityParam));
        boolean needWind = getIntent().getExtras().getBoolean(String.valueOf(R.string.windParam));

        setupCity();

        setupTemperature();

        if(needHumidity) {
            setupHumidity();
        }

        if(needWind) {
            setupWind();
        }
    }

    private void setupWind() {
        TextView windLabel = findViewById(R.id.windLabel);
        windLabel.setText(String.format("%s %s", getResources().getString(R.string.windLabel), getResources().getString(R.string.windValue)));
        windLabel.setVisibility(View.VISIBLE);
    }

    private void setupHumidity() {
        TextView humidityLabel = findViewById(R.id.humidityLabel);
        humidityLabel.setText(String.format("%s %s", getResources().getString(R.string.humidityLabel), getResources().getString(R.string.humidityValue)));
        humidityLabel.setVisibility(View.VISIBLE);
    }

    private void setupTemperature() {
        TextView temperatureLabel = findViewById(R.id.temperatureLabel);
        temperatureLabel.setText(String.format("%s %s", getResources().getString(R.string.temperatureLabel), getResources().getString(R.string.temperatureValue)));
    }

    private void setupCity() {
        String city = Objects.requireNonNull(getIntent().getExtras()).getString(String.valueOf(R.string.cityParam));
        TextView cityLabel = findViewById(R.id.cityLabel);
        cityLabel.setText(String.format("%s %s", getResources().getString(R.string.cityLabel), city));
    }
}

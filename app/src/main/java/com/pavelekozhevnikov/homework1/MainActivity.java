package com.pavelekozhevnikov.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.showWeather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText city = findViewById(R.id.editCity);
                Switch humidity = findViewById(R.id.switchHumidity);
                Switch wind = findViewById(R.id.switchWind);
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                intent.putExtra(String.valueOf(R.string.cityParam), city.getText().toString());
                intent.putExtra(String.valueOf(R.string.humidityParam), humidity.isChecked());
                intent.putExtra(String.valueOf(R.string.windParam), wind.isChecked());
                startActivity(intent);
            }
        });
    }
}

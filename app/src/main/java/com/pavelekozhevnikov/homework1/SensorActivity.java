package com.pavelekozhevnikov.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity {
    private TextView textHumidity;
    private TextView textTemperature;
    private Sensor sensorHumidity;
    private Sensor sensorTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        initViews();
    }

    private void initViews() {
        textHumidity = findViewById(R.id.textHumidity);
        textTemperature = findViewById(R.id.textTemperature);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensorManager!=null) {
            sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            if(sensorHumidity !=null)
                sensorManager.registerListener(listenerHumidity, sensorHumidity,
                        SensorManager.SENSOR_DELAY_NORMAL);
            else
                textHumidity.setText(R.string.no_humidity);
            if(sensorTemperature !=null)
                sensorManager.registerListener(listenerTemperature, sensorTemperature,
                        SensorManager.SENSOR_DELAY_NORMAL);
            else
                textHumidity.setText(R.string.no_temperrature);
        }else{
            textHumidity.setText(R.string.no_humidity);
            textHumidity.setText(R.string.no_temperrature);
        }


    }

    SensorEventListener listenerHumidity = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showHumiditySensors(event);
        }
    };

    SensorEventListener listenerTemperature = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showTemperatureSensors(event);
        }
    };

    private void showHumiditySensors(SensorEvent event){
        @SuppressLint("DefaultLocale") String s = getString(R.string.humidityLabel)+String.format("%.2f",event.values[0]);
        textHumidity.setText(s);
    }

    private void showTemperatureSensors(SensorEvent event){
        @SuppressLint("DefaultLocale") String s= getString(R.string.temperatureLabel)+String.format("%.2f",event.values[0]);
        textTemperature.setText(s);
    }


}

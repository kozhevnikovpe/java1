package com.pavelekozhevnikov.homework1.Model;

import android.content.Context;

import com.pavelekozhevnikov.homework1.R;

import java.io.Serializable;

public class WeatherInfo implements Serializable {

    public int cityId;
    public String cityName;
    public String temperature;
    public String humidity;
    public String pressure;
    public String wind;

    public WeatherInfo(int cityId, Context context) {
        this.cityId = cityId;
        String[] cities = context.getResources().getStringArray(R.array.cities);
        if(cityId>=0 && cityId<cities.length) {
            this.cityName=cities[cityId];
            this.temperature = context.getResources().getString(R.string.temperatureValue);
            this.humidity = context.getResources().getString(R.string.humidityValue);
            this.pressure = context.getResources().getString(R.string.pressureValue);
            this.wind = context.getResources().getString(R.string.windValue);
        }
    }
}

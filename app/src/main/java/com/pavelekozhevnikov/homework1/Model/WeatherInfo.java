package com.pavelekozhevnikov.homework1.Model;

import android.content.Context;

import com.pavelekozhevnikov.homework1.R;

import java.io.Serializable;

public class WeatherInfo implements Serializable {

    public int cityId=-1;
    public String cityName;
    public String temperature;
    public String humidity;
    public String pressure;
    public String wind;
    public String icon;
    String text;
    String date;

    WeatherInfo() {

    }
}

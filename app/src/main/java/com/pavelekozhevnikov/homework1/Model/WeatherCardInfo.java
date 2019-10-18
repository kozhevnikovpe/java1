package com.pavelekozhevnikov.homework1.Model;

import java.io.Serializable;

public class WeatherCardInfo implements Serializable {

    public String temperature;
    public String icon;
    public String day;

    public WeatherCardInfo(String day, String temperature, String icon) {

        this.day = day;
        this.temperature = temperature;
        this.icon = icon;
    }
}

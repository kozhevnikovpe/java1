package com.pavelekozhevnikov.homework1.Model;

import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.pavelekozhevnikov.homework1.App;
import com.pavelekozhevnikov.homework1.Fragment.CitiesFragment;
import com.pavelekozhevnikov.homework1.R;
import com.pavelekozhevnikov.homework1.rest.entities.WeatherRequestRestModel;

import org.json.JSONObject;

import java.util.List;

public class WeatherUpdaterThread extends Thread {
    private Handler handler;
    private double lat,lon;
    private String cityName;
    private FragmentActivity activity;

    public WeatherUpdaterThread(Handler handler, FragmentActivity activity, double lat, double lon) {
        this.handler = handler;
        this.lat = lat;
        this.lon=lon;
        this.activity=activity;
    }

    public WeatherUpdaterThread(Handler handler, FragmentActivity activity, String cityName) {
        this.handler = handler;
        this.cityName = cityName;
        this.activity=activity;
    }

    @Override
    public void run() {
        WeatherDataLoader weatherDataLoader = new WeatherDataLoader();
        WeatherRequestRestModel jsonObject;
        if(cityName!=null)
            jsonObject = weatherDataLoader.getJSONData(cityName);
        else
            jsonObject = weatherDataLoader.getJSONData(lat,lon);
        final WeatherInfo weatherInfo = weatherDataLoader.parseJSONData(jsonObject);
        if(weatherInfo == null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    App.getInstance().warning(R.string.place_not_found, activity);
                }
            });
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Fragment citiesFragment = activity.getSupportFragmentManager().findFragmentById(R.id.cities);
                    if(citiesFragment==null) {
                        List<Fragment> list = activity.getSupportFragmentManager().getFragments();
                        for (Fragment citiesFrag : list) {
                            if (R.id.cities == citiesFrag.getId())
                                citiesFragment = citiesFrag;
                        }
                    }
                    if(citiesFragment!=null)
                        ((CitiesFragment)citiesFragment).showWeather(weatherInfo);
                }
            });
        }
    }
}

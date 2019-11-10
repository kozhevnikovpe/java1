package com.pavelekozhevnikov.homework1.Model;
import android.content.res.Resources;

import com.pavelekozhevnikov.homework1.R;
import com.pavelekozhevnikov.homework1.rest.OpenWeatherRepo;
import com.pavelekozhevnikov.homework1.rest.entities.WeatherRequestRestModel;
import com.pavelekozhevnikov.homework1.rest.entities.WeatherRestModel;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Response;

public class WeatherDataLoader {
    private static final String OPEN_WEATHER_API_KEY = "e45d30c42c463b84b938477f1153ebc3";
    private static final String iconUrl = "https://openweathermap.org/img/wn/%s@2x.png";

    public WeatherRequestRestModel getJSONData(String city) {
        Response response = null;
        try {
            response = OpenWeatherRepo.getSingleton().getAPI().loadWeather(city + ",ru",
                    OPEN_WEATHER_API_KEY, "metric").execute( );
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Objects.requireNonNull(response).body() != null && response.isSuccessful()) {
            return (WeatherRequestRestModel)response.body();
        }else{
            return null;
        }
    }

   public WeatherRequestRestModel getJSONData(double lat, double lon) {
       Response response = null;
       try {
           response = OpenWeatherRepo.getSingleton().getAPI().loadWeather(lat, lon,
                   OPEN_WEATHER_API_KEY, "metric").execute( );
       } catch (IOException e) {
           e.printStackTrace();
       }
       if (Objects.requireNonNull(response).body() != null && response.isSuccessful()) {
           return (WeatherRequestRestModel)response.body();
       }else{
           return null;
       }
    }

    public WeatherInfo parseJSONData(WeatherRequestRestModel jsonObject){
        if(jsonObject==null)
            return null;
        WeatherInfo weather = new WeatherInfo();
        try {
            WeatherRestModel details = jsonObject.weather[0];

            weather.cityName = jsonObject.name.toUpperCase() + ", "
                    + jsonObject.sys.country;
            weather.humidity = jsonObject.main.humidity + "%";
            weather.pressure =  jsonObject.main.pressure + "hPa";
            weather.wind =  jsonObject.wind.speed + "m/s";
            weather.text = details.description.toUpperCase();
            weather.date = getUpdatedDate(jsonObject.dt);
            weather.temperature = String.format(Locale.getDefault(), "%.2f",
                    jsonObject.main.temp) + "\u2103";
            weather.icon = String.format(iconUrl,details.icon);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return weather;
    }

    private String getUpdatedDate(long dt){
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        return dateFormat.format(new Date(dt * 1000));
    }
}

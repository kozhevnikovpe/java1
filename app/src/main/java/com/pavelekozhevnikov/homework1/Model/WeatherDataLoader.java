package com.pavelekozhevnikov.homework1.Model;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import com.pavelekozhevnikov.homework1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherDataLoader {
    private static final String OPEN_WEATHER_API_KEY = "e45d30c42c463b84b938477f1153ebc3";
    private static final String OPEN_WEATHER_API_URL_BY_NAME =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid="+OPEN_WEATHER_API_KEY;
    private static final String OPEN_WEATHER_API_URL_BY_COORDS =
            "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=metric&appid="+OPEN_WEATHER_API_KEY;
    private static final String KEY = "x-api-key";
    private static final String RESPONSE = "cod";
    private static final int ALL_GOOD = 200;

    private JSONObject loadJSONData(URL url){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty(KEY, OPEN_WEATHER_API_KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVariable;

            while ((tempVariable = reader.readLine()) != null) {
                rawData.append(tempVariable).append("\n");
            }

            reader.close();

            JSONObject jsonObject = new JSONObject(rawData.toString());
            if(jsonObject.getInt(RESPONSE) != ALL_GOOD) {
                return null;
            } else {
                return jsonObject;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public JSONObject getJSONData(String city) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_API_URL_BY_NAME, city));
            return loadJSONData(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

   public JSONObject getJSONData(double lat, double lon) {
        try {
            @SuppressLint("DefaultLocale") URL url = new URL(String.format(OPEN_WEATHER_API_URL_BY_COORDS, lat, lon));
            return loadJSONData(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WeatherInfo parseJSONData(JSONObject jsonObject){
        if(jsonObject==null)
            return null;
        WeatherInfo weather = new WeatherInfo();
        try {
            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            JSONObject main = jsonObject.getJSONObject("main");
            JSONObject wind = jsonObject.getJSONObject("wind");

            weather.cityName = jsonObject.getString("name").toUpperCase() + ", "
                    + jsonObject.getJSONObject("sys").getString("country");

            weather.humidity = main.getString("humidity") + "%";

            weather.pressure =  main.getString("pressure") + "hPa";

            weather.wind =  wind.getString("speed") + "m/s";

            weather.text = details.getString("description").toUpperCase();

            weather.date = getUpdatedDate(jsonObject);

            weather.temperature = String.format(Locale.getDefault(), "%.2f",
                    main.getDouble("temp")) + "\u2103";

            weather.icon = getWeatherIcon(details.getInt("id"),
                    jsonObject.getJSONObject("sys").getLong("sunrise") * 1000,
                    jsonObject.getJSONObject("sys").getLong("sunset") * 1000);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return weather;
    }

    private String getUpdatedDate(JSONObject jsonObject) throws JSONException {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        return dateFormat.format(new Date(jsonObject.getLong("dt") * 1000));
    }

    private String getWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";

        if(actualId == 800) {
            long currentTime = new Date().getTime();
            if(currentTime >= sunrise && currentTime < sunset) {
                icon = "\u2600";
                //icon = getString(R.string.weather_sunny);
            } else {
                icon = Resources.getSystem().getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2: {
                    icon = Resources.getSystem().getString(R.string.weather_thunder);
                    break;
                }
                case 3: {
                    icon = Resources.getSystem().getString(R.string.weather_drizzle);
                    break;
                }
                case 5: {
                    icon = Resources.getSystem().getString(R.string.weather_rainy);
                    break;
                }
                case 6: {
                    icon = Resources.getSystem().getString(R.string.weather_snowy);
                    break;
                }
                case 7: {
                    icon = Resources.getSystem().getString(R.string.weather_foggy);
                    break;
                }
                case 8: {
                    icon = "\u2601";
                    // icon = getString(R.string.weather_cloudy);
                    break;
                }
            }
        }
        return icon;
    }

}

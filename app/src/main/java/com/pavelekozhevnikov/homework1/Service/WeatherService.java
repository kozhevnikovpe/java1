package com.pavelekozhevnikov.homework1.Service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.pavelekozhevnikov.homework1.App;
import com.pavelekozhevnikov.homework1.Model.WeatherDataLoader;
import com.pavelekozhevnikov.homework1.Model.WeatherInfo;
import com.pavelekozhevnikov.homework1.R;
import com.pavelekozhevnikov.homework1.rest.entities.WeatherRequestRestModel;

import org.json.JSONObject;

import java.util.Objects;

public class WeatherService extends IntentService {
    public final static String BROADCAST_ACTION_FAILED = "com.pavelekozhevnikov.homework1.WeatherService.failed";
    public final static String BROADCAST_ACTION_SUCCESS = "com.pavelekozhevnikov.homework1.WeatherService.success";

    public WeatherService() {
        super("WeatherService");
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        WeatherDataLoader weatherDataLoader = new WeatherDataLoader();
        WeatherRequestRestModel jsonObject=null;
        String city = Objects.requireNonNull(intent).getStringExtra("city");
        if(city!=null) {
            jsonObject = weatherDataLoader.getJSONData(city);
            Toast.makeText(this, city, Toast.LENGTH_SHORT).show();
        }
        else {
            double lat = intent.getDoubleExtra("lat",1000);
            double lon = intent.getDoubleExtra("lon",1000);
            if(lat!=1000 && lon!=1000){
                jsonObject = weatherDataLoader.getJSONData(lat,lon);
                Toast.makeText(this, lat+":"+lon, Toast.LENGTH_SHORT).show();
            }
        }
        if(jsonObject!=null) {
            final WeatherInfo weatherInfo = weatherDataLoader.parseJSONData(jsonObject);
            if(weatherInfo == null){
                Intent broadcastIntent = new Intent(BROADCAST_ACTION_FAILED);
                sendBroadcast(broadcastIntent);
            }else{
                Intent broadcastIntent = new Intent(BROADCAST_ACTION_SUCCESS);
                broadcastIntent.putExtra("weatherInfo",weatherInfo);
                sendBroadcast(broadcastIntent);
            }
        }else{
            Intent broadcastIntent = new Intent(BROADCAST_ACTION_FAILED);
            /*LocalBroadcastManager.getInstance(this).*/sendBroadcast(broadcastIntent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}

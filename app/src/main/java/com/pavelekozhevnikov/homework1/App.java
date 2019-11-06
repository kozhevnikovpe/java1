package com.pavelekozhevnikov.homework1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.multidex.MultiDexApplication;

public class App extends MultiDexApplication {
    public boolean isDarkTheme=false;
    SharedPreferences sharedPrefs;

    public boolean isFirstRun() {
        return isFirstRun;
    }

    public void setFirstRun(boolean firstRun) {
        isFirstRun = firstRun;
    }

    private boolean isFirstRun=true;

    public SharedPreferences getSharedPrefs() {
        return sharedPrefs;
    }

    public static App getInstance() {
        return instance;
    }

    private static App instance;

    public void toggleTheme(){
        isDarkTheme=!isDarkTheme;
    }

    public App() {
        instance = this;
    }

    public void warning(int messageId, Activity activity){
        Toast toast = Toast.makeText(this, messageId,
                Toast.LENGTH_SHORT);
        LayoutInflater li = LayoutInflater.from(this);
        View toastView = li.inflate(R.layout.custom_toast,(ViewGroup) activity.findViewById(R.id.toastView));
        TextView text = toastView.findViewById(R.id.text);
        text.setText(messageId);
        toast.setView(toastView);
        toast.show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPrefs = getSharedPreferences("weather",MODE_PRIVATE);
    }
}

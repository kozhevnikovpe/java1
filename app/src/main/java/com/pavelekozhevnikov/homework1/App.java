package com.pavelekozhevnikov.homework1;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    public boolean isDarkTheme=false;

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

    public static Context getContext() {
        return instance;
    }
}

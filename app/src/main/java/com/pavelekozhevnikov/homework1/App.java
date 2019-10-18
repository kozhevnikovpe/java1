package com.pavelekozhevnikov.homework1;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static App instance;

    public App() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}

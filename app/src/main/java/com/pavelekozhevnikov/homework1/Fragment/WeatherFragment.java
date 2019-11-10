package com.pavelekozhevnikov.homework1.Fragment;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.pavelekozhevnikov.homework1.Model.WeatherInfo;
import com.pavelekozhevnikov.homework1.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class WeatherFragment extends Fragment {
    static final String WEATHER_INFO = "weatherInfo";

    static WeatherFragment create(WeatherInfo weatherInfo) {
        WeatherFragment f = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(WEATHER_INFO, weatherInfo);
        f.setArguments(args);
        return f;
    }

    WeatherInfo getWeatherInfo() {
        return (WeatherInfo) Objects.requireNonNull(getArguments()).getSerializable(WEATHER_INFO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupCity(view);
        setupTemperature(view);
        setupHumidity(view);
        setupWind(view);
        setupPressure(view);
        setupIcon(view);
    }

    private void setupWind(View view) {
        WeatherInfo weatherInfo = getWeatherInfo();
        TextView windLabel = view.findViewById(R.id.windLabel);
        windLabel.setText(String.format("%s %s", getResources().getString(R.string.windLabel),weatherInfo.wind));
    }

    private void setupHumidity(View view) {
        WeatherInfo weatherInfo = getWeatherInfo();
        TextView humidityLabel = view.findViewById(R.id.humidityLabel);
        humidityLabel.setText(String.format("%s %s", getResources().getString(R.string.humidityLabel), weatherInfo.humidity));
    }

    private void setupTemperature(View view) {
        WeatherInfo weatherInfo = getWeatherInfo();
        TextView temperatureLabel = view.findViewById(R.id.temperatureLabel);
        temperatureLabel.setText(String.format("%s %s", getResources().getString(R.string.temperatureLabel), weatherInfo.temperature));
    }

    private void setupPressure(View view) {
        WeatherInfo weatherInfo = getWeatherInfo();
        TextView temperatureLabel = view.findViewById(R.id.pressureLabel);
        temperatureLabel.setText(String.format("%s %s", getResources().getString(R.string.pressureLabel), weatherInfo.pressure));
    }

    private void setupIcon(View view) {
        WeatherInfo weatherInfo = getWeatherInfo();
        ImageView iconView = view.findViewById(R.id.iconView);
        Picasso.get()
                .load(weatherInfo.icon)
                .fit()
                .into(iconView);
    }

    private void setupCity(View view) {
        WeatherInfo weatherInfo = getWeatherInfo();
        TextView cityLabel = view.findViewById(R.id.cityLabel);
        cityLabel.setText(weatherInfo.cityName);
    }

}

package com.pavelekozhevnikov.homework1.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pavelekozhevnikov.homework1.Model.WeatherInfo;
import com.pavelekozhevnikov.homework1.R;

import java.util.Objects;

public class WeatherFragment extends Fragment {
    static final String WEATHER_INFO = "weatherInfo";
    private TextView iconLabel;

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
        //initRecyclerView(view);

        initViews(view);
        initFonts();
    }

    private void initViews(View view) {
        iconLabel = view.findViewById(R.id.iconLabel);
    }

    private void initFonts() {
        Typeface weatherFont = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), "fonts/weather.ttf");
        iconLabel.setTypeface(weatherFont);
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
        TextView temperatureLabel = view.findViewById(R.id.iconLabel);
        temperatureLabel.setText(weatherInfo.icon);
    }

    private void setupCity(View view) {
        WeatherInfo weatherInfo = getWeatherInfo();
        TextView cityLabel = view.findViewById(R.id.cityLabel);
        cityLabel.setText(weatherInfo.cityName);
    }

    /*private WeatherCardInfo[] getFutureWeatherInfo(){
        String[] wTemperature = Objects.requireNonNull(getActivity()).getResources().getStringArray(R.array.w_temperature);
        String[] wTIcons = getActivity().getResources().getStringArray(R.array.w_icons);
        WeatherCardInfo[] result = new WeatherCardInfo[wTemperature.length];

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(getActivity().getResources().getString(R.string.dateFormat));
        for(int $i=0; $i<wTemperature.length; $i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, $i);
            Date date = calendar.getTime();
            String icon = wTemperature.length==wTIcons.length?wTIcons[$i]:null;
            result[$i] = new WeatherCardInfo(dateFormat.format(date),wTemperature[$i], icon);
        }
        return result;
    }

    private void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Установим адаптер
        RecycledViewAdapter adapter = new RecycledViewAdapter(getFutureWeatherInfo());
        recyclerView.setAdapter(adapter);
    }*/

}

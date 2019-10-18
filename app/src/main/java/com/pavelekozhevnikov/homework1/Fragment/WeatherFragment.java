package com.pavelekozhevnikov.homework1.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pavelekozhevnikov.homework1.Adapter.RecycledViewAdapter;
import com.pavelekozhevnikov.homework1.Model.WeatherCardInfo;
import com.pavelekozhevnikov.homework1.Model.WeatherInfo;
import com.pavelekozhevnikov.homework1.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        Toast.makeText(getActivity(),"onCreateView" ,Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupCity(view);
        setupTemperature(view);
        setupHumidity(view);
        setupWind(view);
        initRecyclerView(view);

        Toast.makeText(getActivity(),"onViewCreated" ,Toast.LENGTH_SHORT).show();
    }

    private void setupWind(View view) {
        WeatherInfo weatherInfo = getWeatherInfo();
        TextView windLabel = view.findViewById(R.id.windLabel);
        windLabel.setText(String.format("%s %s", getResources().getString(R.string.windLabel),weatherInfo.wind));
        windLabel.setVisibility(View.VISIBLE);
    }

    private void setupHumidity(View view) {
        WeatherInfo weatherInfo = getWeatherInfo();
        TextView humidityLabel = view.findViewById(R.id.humidityLabel);
        humidityLabel.setText(String.format("%s %s", getResources().getString(R.string.humidityLabel), weatherInfo.humidity));
        humidityLabel.setVisibility(View.VISIBLE);
    }

    private void setupTemperature(View view) {
        WeatherInfo weatherInfo = getWeatherInfo();
        TextView temperatureLabel = view.findViewById(R.id.temperatureLabel);
        temperatureLabel.setText(String.format("%s %s", getResources().getString(R.string.temperatureLabel), weatherInfo.temperature));
    }

    private void setupCity(View view) {
        WeatherInfo weatherInfo = getWeatherInfo();
        TextView cityLabel = view.findViewById(R.id.cityLabel);
        cityLabel.setText(String.format("%s %s", getResources().getString(R.string.cityLabel), weatherInfo.cityName));
    }

    private WeatherCardInfo[] getFutureWeatherInfo(){
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
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Toast.makeText(context,"onAttach" ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(),"onCreate" ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(getActivity(),"onActivityCreated" ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getActivity(),"onStart" ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(),"onResume" ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getActivity(),"onPause" ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getActivity(),"onStop" ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Toast.makeText(getActivity(),"onDestroyView" ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(),"onDestroy" ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getActivity(),"onDetach" ,Toast.LENGTH_SHORT).show();
    }
}

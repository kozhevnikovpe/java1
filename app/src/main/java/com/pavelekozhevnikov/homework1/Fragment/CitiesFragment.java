package com.pavelekozhevnikov.homework1.Fragment;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.pavelekozhevnikov.homework1.Model.WeatherInfo;
import com.pavelekozhevnikov.homework1.R;
import com.pavelekozhevnikov.homework1.WeatherActivityFragment;

import java.util.Objects;

import static com.pavelekozhevnikov.homework1.Fragment.WeatherFragment.WEATHER_INFO;

public class CitiesFragment extends Fragment {
    private boolean isExistWeatherFrame;  // Можно ли расположить рядом фрагмент с погодой
    private WeatherInfo currentWeatherInfo;
    private ListView listView;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initList();
    }

    private void initViews(View view){
        listView = view.findViewById(R.id.cities_list_view);
        textView = view.findViewById(R.id.cities_list_view_empty);
    }

    private void initList() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()),R.array.cities,
                android.R.layout.simple_list_item_activated_1);
        listView.setAdapter(adapter);
        listView.setEmptyView(textView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentWeatherInfo = new WeatherInfo(position, Objects.requireNonNull(getActivity()));
                //showWeather(currentWeatherInfo);
                Snackbar.make(view, String.format(getString(R.string.cityConfirmation), listView.getItemAtPosition(position).toString()), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.confirmBtn), new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                showWeather(currentWeatherInfo);
                            }
                        }).show();

            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isExistWeatherFrame = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentWeatherInfo = (WeatherInfo) savedInstanceState.getSerializable(WEATHER_INFO);
        }else{
            currentWeatherInfo = new WeatherInfo(0, Objects.requireNonNull(getActivity()));
        }

        if (isExistWeatherFrame) {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showWeather(currentWeatherInfo);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(WEATHER_INFO, currentWeatherInfo);
        super.onSaveInstanceState(outState);
    }

    // Показать погоду. Ecли возможно, то показать рядом со списком,
    // если нет, то открыть вторую activity
    private void showWeather(WeatherInfo weatherInfo) {
        if (isExistWeatherFrame) {
            listView.setItemChecked(weatherInfo.cityId,true);
            // Проверим, что фрагмент с погодой существует в activity
            WeatherFragment detail = (WeatherFragment)
                    Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.weather_frame);

            // Если есть необходимость, то выведем погоду
            if (detail == null || detail.getWeatherInfo().cityId != weatherInfo.cityId) {
                // Создаем новый фрагмент с текущей позицией для вывода погоды
                detail = WeatherFragment.create(weatherInfo);

                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.weather_frame, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            // Если нельзя вывести погоду рядом, откроем вторую activity
            Intent intent = new Intent();
            intent.setClass(Objects.requireNonNull(getActivity()), WeatherActivityFragment.class);
            // и передадим туда параметры
            intent.putExtra(WEATHER_INFO, weatherInfo);
            startActivity(intent);
        }
    }
}

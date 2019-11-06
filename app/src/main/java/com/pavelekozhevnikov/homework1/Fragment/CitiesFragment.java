package com.pavelekozhevnikov.homework1.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.material.snackbar.Snackbar;
import com.pavelekozhevnikov.homework1.App;
import com.pavelekozhevnikov.homework1.Model.WeatherInfo;
import com.pavelekozhevnikov.homework1.Model.WeatherUpdaterThread;
import com.pavelekozhevnikov.homework1.R;
import com.pavelekozhevnikov.homework1.Service.WeatherService;
import com.pavelekozhevnikov.homework1.WeatherActivityFragment;

import java.util.Objects;

import static com.pavelekozhevnikov.homework1.Fragment.WeatherFragment.WEATHER_INFO;

public class CitiesFragment extends Fragment {
    private boolean isExistWeatherFrame;  // Можно ли расположить рядом фрагмент с погодой
    private WeatherInfo currentWeatherInfo;
    private ListView listView;
    private TextView textView;
    private Handler handler = new Handler();
    private BroadcastReceiver mMessageReceiver;
    private Bundle instanceState = null;


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
        initReceiver();
        if(App.getInstance().isFirstRun()) {
            runWeatherInCityActivity(App.getInstance().getSharedPrefs().getInt("selectedCity", 0));
            App.getInstance().setFirstRun(false);
        }
    }

    private void initReceiver() {
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                WeatherInfo weatherInfo = (WeatherInfo) intent.getSerializableExtra("weatherInfo");
                if(weatherInfo!=null)
                    showWeather(weatherInfo);
                else{
                    App.getInstance().warning(R.string.place_not_found, getActivity());
                }
            }
        };
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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Snackbar.make(view, String.format(getString(R.string.cityConfirmation), listView.getItemAtPosition(position).toString()), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.confirmBtn), new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                SharedPreferences.Editor editor = App.getInstance().getSharedPrefs().edit();
                                editor.putInt("selectedCity", position);
                                runWeatherInCityActivity(position);
                                editor.commit();
                            }
                        }).show();

            }
        });
    }

    private void runWeatherInCityActivity(int position) {
        //new WeatherUpdaterThread(handler, getActivity(), listView.getItemAtPosition(position).toString()).start();
        Intent intent = new Intent(getActivity(), WeatherService.class);
        intent.putExtra("city", listView.getItemAtPosition(position).toString());
        Objects.requireNonNull(getActivity()).startService(intent);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instanceState = savedInstanceState;
    }


    @Override
    public void onResume() {
        super.onResume();

        getActivity().registerReceiver(mMessageReceiver, new IntentFilter(WeatherService.BROADCAST_ACTION_FAILED));
        getActivity().registerReceiver(mMessageReceiver, new IntentFilter(WeatherService.BROADCAST_ACTION_SUCCESS));

        isExistWeatherFrame = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        if (instanceState != null) {
            // Восстановление текущей позиции.
            currentWeatherInfo = (WeatherInfo) instanceState.getSerializable(WEATHER_INFO);
        }
        if (isExistWeatherFrame && currentWeatherInfo==null) {
            new WeatherUpdaterThread(handler, getActivity(), listView.getItemAtPosition(0).toString()).start();
            listView.setItemChecked(0,true);
        }

        if (isExistWeatherFrame) {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            if(currentWeatherInfo!=null)
                showWeather(currentWeatherInfo);
        }
    }

    public void onPause() {
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(WEATHER_INFO, currentWeatherInfo);
        super.onSaveInstanceState(outState);
    }

    // Показать погоду. Ecли возможно, то показать рядом со списком,
    // если нет, то открыть вторую activity
    public void showWeather(WeatherInfo weatherInfo) {
        currentWeatherInfo = weatherInfo;
        if (isExistWeatherFrame) {
            listView.setItemChecked(weatherInfo.cityId,true);

            // Проверим, что фрагмент с погодой существует в activity
            WeatherFragment detail = (WeatherFragment)
                    Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.weather_frame);

            // Если есть необходимость, то выведем погоду
            if (detail == null || !detail.getWeatherInfo().cityName.equals(weatherInfo.cityName)) {
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

package com.pavelekozhevnikov.homework1.rest;

import com.pavelekozhevnikov.homework1.rest.entities.WeatherRequestRestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequestRestModel> loadWeather(@Query("q") String city,
                                              @Query("appid") String keyApi,
                                              @Query("units") String units);

    @GET("data/2.5/weather")
    Call<WeatherRequestRestModel> loadWeather(@Query("lat") double lat,
                                              @Query("lon") double lon,
                                              @Query("appid") String keyApi,
                                              @Query("units") String units);
}

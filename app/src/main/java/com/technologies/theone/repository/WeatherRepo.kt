package com.technologies.theone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.technologies.theone.api.getWeather
import com.technologies.theone.model.WeatherModel

class WeatherRepo(private val api: getWeather) {
    private val mutuableLiveData = MutableLiveData<WeatherModel>()

    val liveData: LiveData<WeatherModel>
        get() = mutuableLiveData

    suspend fun getData(
        lat: Double,
        lon: Double,
        units: String,
        appid: String,
    ) {
        val result = api.getWeatherData(lat, lon, units, appid)
        if (result?.body() != null){
            mutuableLiveData.postValue(result.body())
        }
    }
}
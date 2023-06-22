package com.technologies.theone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technologies.theone.model.WeatherModel
import com.technologies.theone.repository.WeatherRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel( private val repo: WeatherRepo): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getData(12.9082847623315, 77.65197822993314, "imperial", "b143bb707b2ee117e62649b358207d3e")

        }
    }

    val weatherLiveData: LiveData<WeatherModel>
    get() = repo.liveData
}
package com.technologies.theone

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.technologies.theone.api.RetrofitHelper
import com.technologies.theone.api.getWeather
import com.technologies.theone.databinding.FragmentWeatherBinding
import com.technologies.theone.repository.WeatherRepo
import com.technologies.theone.utils.Constance
import com.technologies.theone.viewmodel.WeatherViewModel
import com.technologies.theone.viewmodel.WeatherViewModelFactory


class Weather : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: WeatherViewModel
    var sp: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        //setHasOptionsMenu(true)

        binding.weatherToolbar.setNavigationOnClickListener({
            findNavController().navigate(R.id.action_weather_to_userLists)
        })

        binding.weatherToolbar.inflateMenu(R.menu.menu_logout)
        binding.weatherToolbar.setOnMenuItemClickListener({
            when (it.itemId) {
                R.id.logout ->{
                    Toast.makeText(context, "User logout.", Toast.LENGTH_LONG).show()
                    sp = context?.getSharedPreferences(Constance.SP_NAME, Context.MODE_PRIVATE)
                    editor = sp?.edit().apply {
                        this?.clear()
                        this?.apply()
                    }
                    findNavController().navigate(R.id.action_weather_to_login)
                    return@setOnMenuItemClickListener true
                }

                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        })

        val apiService = RetrofitHelper.getRetrofit().create(getWeather::class.java)
        val repo = WeatherRepo(apiService)
        viewModel =
            ViewModelProvider(this, WeatherViewModelFactory(repo)).get(WeatherViewModel::class.java)

        viewModel.weatherLiveData.observe(this, Observer {
            binding.tvData.text =
                "Temp :- ${it.current.temp.toString()} \nWeather type :- ${it.current.weather.get(0).main}" +
                        "\nHumidity :- ${it.current.humidity.toString()} \nWind Speed :- ${it.current.wind_speed.toString()}"
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu_logout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                Toast.makeText(context, "User logout.", Toast.LENGTH_LONG).show()
                sp = context?.getSharedPreferences(Constance.SP_NAME, Context.MODE_PRIVATE)
                editor = sp?.edit().let {
                    it?.remove("username")
                }
                findNavController().navigate(R.id.action_weather_to_login)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
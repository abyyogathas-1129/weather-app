package com.example.weatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var etCity: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvCityName: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWindSpeed: TextView

    private val apiKey = "1e023546e6c1ec286ff2927d0571f4b3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCity = findViewById(R.id.etCity)
        btnSearch = findViewById(R.id.btnSearch)
        tvCityName = findViewById(R.id.tvCityName)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvCondition = findViewById(R.id.tvCondition)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvWindSpeed = findViewById(R.id.tvWindSpeed)

        btnSearch.setOnClickListener {

            val city = etCity.text.toString().trim()

            if (city.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter a city name",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                getWeather(city)
            }
        }
    }

    private fun getWeather(city: String) {

        RetrofitClient.api.getWeather(
            city,
            apiKey
        ).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {

                if (response.isSuccessful) {

                    val weatherData = response.body()

                    if (weatherData != null) {

                        tvCityName.text = weatherData.name
                        tvTemperature.text = "${weatherData.main.temp}°C"

                        tvCondition.text = weatherData.weather[0].main

                        tvHumidity.text = "${weatherData.main.humidity}%"

                        tvWindSpeed.text = "${weatherData.wind.speed} m/s"
                    }

                } else {

                    Toast.makeText(
                        this@MainActivity,
                        "City not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<WeatherResponse>,
                t: Throwable
            ) {

                Toast.makeText(
                    this@MainActivity,
                    "Network error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
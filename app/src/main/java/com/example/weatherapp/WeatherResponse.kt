package com.example.weatherapp

data class WeatherResponse(
    val name: String,
    val main: MainData,
    val wind: WindData,
    val weather: List<WeatherData>
)

data class MainData(
    val temp: Double,
    val humidity: Int
)

data class WindData(
    val speed: Double
)

data class WeatherData(
    val main: String,
    val description: String
)
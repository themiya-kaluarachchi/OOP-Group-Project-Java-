package com.example.javaminiproject;

/**
 * @author Kivilak Chathuranga
 */


public class WeatherInfo {
    private final double temp;
    private final double tempMin;
    private final double tempMax;
    private final double feelsLike;
    private final int humidity;
    private final double pressure;
    private final double windSpeed;
    private final String main;
    private final String description;
    private final String iconName;

    public WeatherInfo(double temp, double tempMin, double tempMax, double feelsLike, int humidity, double pressure, double windSpeed, String main, String description, String iconName) {
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.main = main;
        this.description = description;
        this.iconName = iconName;
    }

    public String getTemp() {
        return String.valueOf(temp);
    }

    public String getTempMin() {
        return String.valueOf(tempMin);
    }

    public String getTempMax() {
        return String.valueOf(tempMax);
    }

    public String getFeelsLike() {
        return String.valueOf(feelsLike);
    }

    public String getHumidity() {
        return String.valueOf(humidity);
    }

    public String getPressure() {
        return String.valueOf(pressure);
    }

    public String getWindSpeed() {
        return String.valueOf(windSpeed);
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIconName() {
        return iconName;
    }
}
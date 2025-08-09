package com.example.javaminiproject;

/**
 * @author Kivilak Chathuranga
 */

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class FetchWeatherInfo {
    private final String API_KEY;
    private final String UNITS = "&units=metric";
    private final String APPID = "&appid=";

    public FetchWeatherInfo() {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("API_KEY");
    }

    public WeatherInfo getCurrentWeatherInfo(String location) { // get live weather info
        WeatherInfo weatherInfo = null;
        String CURRENT_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
        String urlString = CURRENT_URL + location + APPID + API_KEY + UNITS;

        try {
            JSONObject weatherData = new JSONObject(makeHttpRequest(urlString)); // fetch the API data
            //System.out.println(weatherData.toString(2));

            JSONObject main = weatherData.getJSONObject("main");

            double temp = main.getDouble("temp");
            double tempMin = main.getDouble("temp_min");
            double tempMax = main.getDouble("temp_max");
            double feelsLike = main.getDouble("feels_like");
            int humidity = main.getInt("humidity");
            double pressure = main.getDouble("pressure");

            double windSpeed = weatherData.getJSONObject("wind").getDouble("speed");

            JSONArray weather = weatherData.getJSONArray("weather");

            String mainDes = weather.getJSONObject(0).getString("main");
            String description = weather.getJSONObject(0).getString("description");
            String icon = weather.getJSONObject(0).getString("icon");

            weatherInfo = new WeatherInfo(temp, tempMin, tempMax, feelsLike, humidity, pressure, windSpeed, mainDes, description, icon);
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }

        return weatherInfo;
    }

    public WeatherInfo[] getWeekWeather(String location) { // get weekly weather info
        WeatherInfo[] weatherInfos = new WeatherInfo[7];
        String WEEK_URL = "https://pro.openweathermap.org/data/2.5/forecast/daily?q=";
        String CNT = "&cnt=7";
        String urlString = WEEK_URL + location + CNT + APPID + API_KEY + UNITS;

        try {
            JSONObject weatherData = new JSONObject(makeHttpRequest(urlString));
            JSONArray list = weatherData.getJSONArray("list");

            for (int i = 0; i < list.length(); i++) {
                JSONObject dayData = list.getJSONObject(i);
                JSONObject main = dayData.getJSONObject("temp");

                double temp = main.getDouble("day");
                double tempMin = main.getDouble("min");
                double tempMax = main.getDouble("max");

                double feelsLike = dayData.getJSONObject("feels_like").getDouble("day");
                int humidity = dayData.getInt("humidity");
                double pressure = dayData.getDouble("pressure");
                double windSpeed = dayData.getDouble("speed");

                JSONArray weather = dayData.getJSONArray("weather");

                String mainDes = weather.getJSONObject(0).getString("main");
                String description = weather.getJSONObject(0).getString("description");
                String icon = weather.getJSONObject(0).getString("icon");

                weatherInfos[i] = new WeatherInfo(temp, tempMin, tempMax, feelsLike, humidity, pressure, windSpeed, mainDes, description, icon);
            }

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }

        return weatherInfos;
    }

    private String makeHttpRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        StringBuilder content = new StringBuilder();
        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
        } else {
            throw new IOException("HTTP request failed with response code: " + responseCode);
        }

        return content.toString();
    }
}

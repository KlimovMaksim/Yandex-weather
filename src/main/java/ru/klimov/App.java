package ru.klimov;


import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App
{
    static HttpClient httpClient = HttpClient.newHttpClient();
    static String testToken = "Your token";


    public static void main(String[] args) {
        getAllData();

        getTemp();

        getAvgTempFor5Days();
    }

    public static void getAllData() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.yandex.ru/v2/forecast?lat=52.37125&lon=4.89388"))
                .headers("X-Yandex-Weather-Key", testToken)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("\nAll data form request.");
            System.out.println("Response code: " + response.statusCode());
            System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void getTemp() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.yandex.ru/v2/forecast?lat=52.37125&lon=4.89388"))
                .headers("X-Yandex-Weather-Key", testToken)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseBody = new JSONObject(response.body());
            System.out.println("\nTemperature form request.");
            System.out.println("Response code: " + response.statusCode());
            System.out.println("Response temp: " + responseBody.getJSONObject("fact").get("temp"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void getAvgTempFor5Days() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.yandex.ru/v2/forecast?lat=52.37125&lon=4.89388&limit=5&hours=false"))
                .headers("X-Yandex-Weather-Key", testToken)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseBody = new JSONObject(response.body());
            System.out.println("\nAverage temperature for 5 days form request.");
            System.out.println("Response code: " + response.statusCode());
            double avgTemp = 0;
            for (int i = 0; i < 5; i++) {
                int avgDayTemp = (int) responseBody
                        .getJSONArray("forecasts")
                        .optJSONObject(i)
                        .getJSONObject("parts")
                        .getJSONObject("day")
                        .get("temp_avg");
                avgTemp += avgDayTemp;
            }

            System.out.println("Response avg temp: " + avgTemp / 5);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

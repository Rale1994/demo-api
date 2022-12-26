package com.example.demo.demoapi.shared;

public class Utils {
    public static final double KELVIN_TEMPERATURE = 273.15;

    public static Double convertToCelsius(double temperature) {
        return temperature - KELVIN_TEMPERATURE;
    }
}

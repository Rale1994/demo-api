package com.example.demo.demoapi.shared;

public class EmailBaseParameters {

    public static final String SUB_SUBJECT = "Thank you for subscribe";
    public static final String SUB_MESSAGE = "Now you are subscribe to our weather news!";

    public static String generateMessageForMail(double temperature, double maxTemp, double minTemp, double feelsLike) {
        return String.format("Current temperature is: "+temperature+" celsius" +
                ",\n minimum temperature: "+minTemp+" celsius," +
                "\n maximum temperature:"+maxTemp+" celsius" +
                "\n feels like: "+feelsLike+" celsius");
    }
    public static String generateSubjectForMail(String name) {
        return String.format("Weather information for: "+name+"");
    }
}

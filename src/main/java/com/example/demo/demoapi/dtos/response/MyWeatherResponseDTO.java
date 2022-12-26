package com.example.demo.demoapi.dtos.response;
import com.example.demo.demoapi.entity.City;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MyWeatherResponseDTO {
    private String name;
    private int temp;
    private int feelsLike;
    private int tempMax;
    private int tempMin;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastWeatherInfoDate;


    public MyWeatherResponseDTO(City city) {
        this.name=city.getName();
        this.temp=city.getTemp();
        this.feelsLike=city.getFeelsLike();
        this.tempMax=city.getTempMax();
        this.tempMin=city.getTempMin();
        this.description=city.getWeatherDescription();
        this.lastWeatherInfoDate=city.getLastWeatherInfoDate();

    }
}

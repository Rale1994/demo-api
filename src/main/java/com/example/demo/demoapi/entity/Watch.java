package com.example.demo.demoapi.entity;

import com.example.demo.demoapi.dtos.request.WatchRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "watches")
@AllArgsConstructor
@NoArgsConstructor
public class Watch {
    @Id
    @GeneratedValue
    private long id;
    private String brand;
    private String gender;

    private String country;
    @ManyToOne
    @JoinColumn(name = "usersId")
    private User user;

    public Watch(WatchRequest watchRequest) {
        this.brand = watchRequest.getBrand();
        this.gender = watchRequest.getGender();
        this.country = watchRequest.getCountry();
    }
}

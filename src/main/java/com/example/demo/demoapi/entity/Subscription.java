package com.example.demo.demoapi.entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue
    private long id;

    private String type;

    private String city;

    @ManyToOne
    @JoinColumn(name = "usersId")
    private User user;

    public Subscription(String type, String city, User user) {
        this.type = type;
        this.city = city;
        this.user = user;
    }

}

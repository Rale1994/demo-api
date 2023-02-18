package com.example.demo.demoapi.entity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue
    private long id;

    private String type;

    @ManyToOne
    @JoinColumn(name = "usersId")
    private User user;

    public Subscription(String type, User user) {
        this.type = type;
        this.user = user;
    }
}

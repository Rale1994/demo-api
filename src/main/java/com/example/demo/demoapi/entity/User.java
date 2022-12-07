package com.example.demo.demoapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    private String role;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    private List<News> news;

}

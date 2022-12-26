package com.example.demo.demoapi.entity;

import com.example.demo.demoapi.dtos.request.UserDetailsRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;


@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public User(UserDetailsRequestDTO userRequest) {
        this.firstName = userRequest.getFirstName();
        this.lastName = userRequest.getLastName();
        this.email = userRequest.getEmail();
        this.username = userRequest.getUsername();
        this.password = userRequest.getPassword();
        this.role = userRequest.getRole();
    }

    public User(long id, String firstName, String lastName, String email, String username, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

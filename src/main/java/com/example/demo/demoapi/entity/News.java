package com.example.demo.demoapi.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@ToString
@Entity(name = "news")
public class News {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    private LocalDateTime date;

    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

}

package com.example.demo.demoapi.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "news")
public class News {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "usersId")
    private User user;

}

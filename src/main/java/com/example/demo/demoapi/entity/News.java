package com.example.demo.demoapi.entity;

import com.example.demo.demoapi.dtos.request.NewsRequest;
import com.example.demo.demoapi.dtos.request.NewsUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "news")
@AllArgsConstructor
@NoArgsConstructor
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

    public News(NewsRequest newsRequest) {
        this.title= newsRequest.getTitle();
        this.createdDate= newsRequest.getCreatedDate();
        this.updatedDate=createdDate;
        User user= new User();
        user.setId(newsRequest.getUsersId());
        this.user=user;
    }

    public News(NewsUpdateRequest newsUpdateRequest) {
        this.title= newsUpdateRequest.getTitle();
        this.updatedDate= newsUpdateRequest.getUpdatedDate();
    }
}

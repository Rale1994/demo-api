package com.example.demo.demoapi.entity;

import com.example.demo.demoapi.dtos.request.NewsRequestDTO;
import com.example.demo.demoapi.dtos.request.NewsUpdateRequestDTO;
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

    public News(NewsRequestDTO newsRequestDTO) {
        this.title=newsRequestDTO.getTitle();
        this.createdDate= newsRequestDTO.getCreatedDate();
        this.updatedDate=createdDate;
        User user= new User();
        user.setId(newsRequestDTO.getUsersId());
        this.user=user;
    }

    public News(NewsUpdateRequestDTO newsUpdateRequestDTO) {
        this.title=newsUpdateRequestDTO.getTitle();
        this.updatedDate=newsUpdateRequestDTO.getUpdatedDate();
    }
}

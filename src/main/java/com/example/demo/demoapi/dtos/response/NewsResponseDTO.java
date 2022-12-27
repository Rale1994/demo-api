package com.example.demo.demoapi.dtos.response;
import com.example.demo.demoapi.entity.News;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NewsResponseDTO {

    private long id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedDate;

    private UserNewsResponseDTO user;

    public NewsResponseDTO(News news) {
        this.id= news.getId();
        this.title=news.getTitle();
        this.createdDate=news.getCreatedDate();
        this.updatedDate=news.getUpdatedDate();

    }
}

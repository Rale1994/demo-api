package com.example.demo.demoapi.dtos.request;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class NewsRequest {

    @NotBlank(message = "Title is required!")
    private String title;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;

    @NotNull(message = "User id is required")
    private long usersId;

}

package com.example.demo.demoapi.dtos.request;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class NewsUpdateRequestDTO {
    @NotBlank(message = "Name is required!")
    private String title;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime updatedDate;
}

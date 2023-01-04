package com.example.demo.demoapi.dtos.response;

import com.example.demo.demoapi.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserNewsResponseDTO {
    String firstName;
    String lastName;

    public UserNewsResponseDTO(User user) {
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
    }
}

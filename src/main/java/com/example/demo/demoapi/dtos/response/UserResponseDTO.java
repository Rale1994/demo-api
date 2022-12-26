package com.example.demo.demoapi.dtos.response;
import com.example.demo.demoapi.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserResponseDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}

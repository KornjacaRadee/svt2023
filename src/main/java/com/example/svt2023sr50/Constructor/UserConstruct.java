package com.example.svt2023sr50.Constructor;

import com.example.svt2023sr50.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConstruct {

    private Long userId;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;


    public UserConstruct(User createdUser) {
        this.userId = createdUser.getUserId();
        this.username = createdUser.getUsername();
        this.email = createdUser.getEmail();
        this.firstName = createdUser.getFirstName();
        this.lastName = createdUser.getLastName();
    }
}
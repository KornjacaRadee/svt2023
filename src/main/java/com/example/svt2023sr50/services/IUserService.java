package com.example.svt2023sr50.services;

import com.example.svt2023sr50.Constructor.UserConstruct;
import com.example.svt2023sr50.model.User;

public interface IUserService {

    User geOne(Long userId);

    User findByUsername(String username);


    User createUser(UserConstruct userDTO);
}
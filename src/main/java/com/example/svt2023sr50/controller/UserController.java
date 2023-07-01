package com.example.svt2023sr50.controller;

import com.example.svt2023sr50.model.Post;
import com.example.svt2023sr50.model.Role;
import com.example.svt2023sr50.model.User;
import com.example.svt2023sr50.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService service;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @PutMapping("/updatepassword")
    public ResponseEntity<User> create(@RequestBody User newUser) {
        newUser.setRole(Role.USER);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User updatedUser = service.save(newUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }
}

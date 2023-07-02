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
    @PutMapping("/updatepassword/{old}")
    public ResponseEntity<User> create(@RequestBody User newUser,@PathVariable("old") String oldPassword) {

        String encodedPassword = service.geOne(newUser.getUserId()).getPassword();

        boolean match = passwordEncoder.matches(oldPassword, encodedPassword);
        System.out.println(oldPassword);
        System.out.println(service.geOne(newUser.getUserId()).getPassword());
        if(match){
            newUser.setRole(Role.USER);
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            User updatedUser = service.save(newUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(newUser, HttpStatus.NOT_ACCEPTABLE);
        }

    }
}

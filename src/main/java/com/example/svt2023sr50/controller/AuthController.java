package com.example.svt2023sr50.controller;


import com.example.svt2023sr50.Constructor.LoginRequest;
import com.example.svt2023sr50.Constructor.RegisterRequest;
import com.example.svt2023sr50.Constructor.UserTokenState;
import com.example.svt2023sr50.model.User;
import com.example.svt2023sr50.services.AuthService;
import com.example.svt2023sr50.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200/")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Validated RegisterRequest registerRequest){
        authService.singup(registerRequest);
        return new ResponseEntity<>("Register successful", HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> login(@RequestBody LoginRequest loginrequest){
        return authService.login(loginrequest);

    }
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public User user(Principal user) {
        return this.userService.findByUsername(user.getName());
    }
}

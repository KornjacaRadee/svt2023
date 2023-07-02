package com.example.svt2023sr50.controller;

import com.example.svt2023sr50.model.Group;
import com.example.svt2023sr50.model.Post;
import com.example.svt2023sr50.model.User;
import com.example.svt2023sr50.services.AuthService;
import com.example.svt2023sr50.services.GroupService;
import com.example.svt2023sr50.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/group")
@AllArgsConstructor
public class GroupController {

    @Autowired
    GroupService service;

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @PostMapping("/new")
    public ResponseEntity<Group> create(@RequestBody Group newGroup) {
        Group addedPost = groupService.save(newGroup);
        return new ResponseEntity<>(addedPost, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Group>> allGroups() {
        return new ResponseEntity<>(groupService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{id}")
    public ResponseEntity<Group> groupById(@PathVariable("id") Long id) {
        Group addedPost = groupService.getGroup(id);
        return new ResponseEntity<>(addedPost, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>("Deleted", HttpStatus.GONE);
    }

}

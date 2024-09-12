package com.example.svt2023sr50.controller;

import com.example.svt2023sr50.indeexmodel.PostIndex;
import com.example.svt2023sr50.indexrepository.PostIndexRepository;
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

    @Autowired
    PostIndexRepository indexRepository;

    @PostMapping("/new")
    public ResponseEntity<Group> create(@RequestBody Group newGroup) {
        Group addedPost = groupService.save(newGroup);
        return new ResponseEntity<>(addedPost, HttpStatus.CREATED);
    }

    @PostMapping("/new-post")
    public ResponseEntity<List<Post>> createPost(@RequestBody Group newGroup) {
        Group addedPost = groupService.save(newGroup);
        List<Post> posts = addedPost.getPosts();
        Post post = posts.get(posts.size() - 1);
        PostIndex index = new PostIndex();
        index.setId(post.getPostId());
        index.setPostName(post.getPostName());
        index.setContent(post.getContent());
        indexRepository.save(index);

        return new ResponseEntity<>(addedPost.getPosts(), HttpStatus.CREATED);
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

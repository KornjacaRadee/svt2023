package com.example.svt2023sr50.controller;


import com.example.svt2023sr50.model.Comment;
import com.example.svt2023sr50.model.Post;
import com.example.svt2023sr50.services.CommentService;
import com.example.svt2023sr50.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {
    private final PostService service;

    private final CommentService commentService;
    @PostMapping("/new")
    public ResponseEntity<Post> create(@RequestBody Post newPost) {
        Post addedPost = service.save(newPost);
        return new ResponseEntity<>(addedPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<Post> updatePost(@RequestBody Post updatedPost) {
        Post newPost = service.save(updatedPost);
        return new ResponseEntity<>(newPost, HttpStatus.OK);
    }

    @GetMapping("/postpage/{id}")
    public ResponseEntity<Post> postpage(@PathVariable Long id) {
        Post post = service.getPost(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/allposts")
    public ResponseEntity<List<Post>> allposts(){
        List<Post> posts = service.getAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/groupposts/{id}")
    public ResponseEntity<List<Post>> posts(Long id) {
        List<Post> posts = service.getGroupPosts(id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<Comment> comment(@PathVariable("id") Long id, @RequestBody Comment comment) {
        Post post = service.getPost(id);
        List<Comment> comments = new ArrayList<>();
        if(!post.getComments().isEmpty()){
             comments = post.getComments();
        }
        comment.setPost(post);
        comments.add(comment);
        post.setComments(comments);
        service.save(post);
//        commentService.save(comment);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

}

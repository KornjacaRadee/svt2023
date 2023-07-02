package com.example.svt2023sr50.controller;


import com.example.svt2023sr50.model.*;
import com.example.svt2023sr50.services.CommentService;
import com.example.svt2023sr50.services.GroupService;
import com.example.svt2023sr50.services.LikeService;
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

    private final LikeService likeService;

    private final GroupService groupService;
    @PostMapping("/new")
    public ResponseEntity<Post> create(@RequestBody Post newPost) {
        Post addedPost = service.save(newPost);
        return new ResponseEntity<>(addedPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") Long id) {
        Post post = service.getPost(id);
        Group group = groupService.getGroupByPost(post);
        if (group != null) {
            group.getPosts().remove(post);
            groupService.save(group);
        }

        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<Post> updatePost(@RequestBody Post updatedPost) {
        Post newPost = service.save(updatedPost);
        return new ResponseEntity<>(newPost, HttpStatus.OK);
    }


    @GetMapping("/allposts")
    public ResponseEntity<List<Post>> allposts(){
        List<Post> posts = service.getAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @PutMapping("/comment/{id}")
    public ResponseEntity<Comment> comment(@PathVariable("id") Long id, @RequestBody Comment comment) {
        Post post = service.getPost(id);
        comment.setPost(post);
        post.getComments().add(comment);
        service.save(post);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PutMapping("/like/{id}/{userId}")
    public ResponseEntity<Like> like(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        Post post = service.getPost(id);

        Like like = new Like();
        like.setPost(post);
        like.setUserId(userId);
        post.getLikes().add(like);
        service.save(post);



        return new ResponseEntity<>(like, HttpStatus.CREATED);
    }

    @DeleteMapping("/unlike/{id}/{userId}")
    public ResponseEntity<?> unlike(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        Post post = service.getPost(id);
        List<Like> likes = post.getLikes();
        Like likeToRemove = new Like();

        for (Like like : likes) {
            if (like.getUserId().equals(userId)) {
                likeToRemove = like;
                break;
            }
        }
            likes.remove(likeToRemove);
            likeService.delete(likeToRemove);
            service.save(post);
            return new ResponseEntity<>(HttpStatus.OK);
    }
}






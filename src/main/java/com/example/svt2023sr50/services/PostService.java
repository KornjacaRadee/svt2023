package com.example.svt2023sr50.services;

import com.example.svt2023sr50.model.Group;
import com.example.svt2023sr50.model.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.svt2023sr50.Constructor.LoginRequest;
import com.example.svt2023sr50.Constructor.RegisterRequest;
import com.example.svt2023sr50.model.Role;
import com.example.svt2023sr50.model.User;
import com.example.svt2023sr50.security.*;
import com.example.svt2023sr50.repository.*;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {


    public final PostRepository postRepository;

    public final GroupRepository groupRepository;

    public List<Post> getAll() {
        return postRepository.findAll();
    }
    public Post getPost(Long id) {
        return postRepository.findById(id).get();
    }
    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }


    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }



    public List<Post> getGroupPosts(Long id) {
        Group group = groupRepository.findById(id).get();
        return group.getPosts();
    }

}

package com.example.svt2023sr50.services;

import com.example.svt2023sr50.indeexmodel.PostIndex;
import com.example.svt2023sr50.indexrepository.PostIndexRepository;
import com.example.svt2023sr50.model.Group;
import com.example.svt2023sr50.model.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.svt2023sr50.repository.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {


    public final PostRepository postRepository;

    public final GroupRepository groupRepository;

    public final PostIndexRepository indexRepository;

    private static final Logger loggir = LoggerFactory.getLogger(PostService.class);




    public List<Post> getAll() {
        return postRepository.findAll();
    }
    public Post getPost(Long id) {
        return postRepository.findById(id).get();
    }

    @Transactional
    public Post save(Post post) {
        // Save the post in the database
        Post savedPost = postRepository.save(post);

        // Index the post in Elasticsearch
        PostIndex postIndex = new PostIndex();
        postIndex.setId(savedPost.getPostId());
        postIndex.setPostName(savedPost.getPostName());
        postIndex.setContent(savedPost.getContent());
        try {
            indexRepository.save(postIndex);
            loggir.info("PostIndex saved successfully.");
        } catch (Exception e) {
            loggir.error("PostIndex not saved successfully.",e);
        }


        return savedPost;
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

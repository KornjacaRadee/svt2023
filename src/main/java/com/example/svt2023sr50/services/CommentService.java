package com.example.svt2023sr50.services;

import com.example.svt2023sr50.model.Comment;
import com.example.svt2023sr50.model.Post;
import com.example.svt2023sr50.repository.CommentRepository;
import com.example.svt2023sr50.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    @Transactional
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    @Transactional
    public void delete(Comment comment){repository.delete(comment);}

}

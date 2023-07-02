package com.example.svt2023sr50.services;

import com.example.svt2023sr50.model.Comment;
import com.example.svt2023sr50.model.Like;
import com.example.svt2023sr50.repository.CommentRepository;
import com.example.svt2023sr50.repository.LikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository repository;

    @Transactional
    public Like save(Like like) {
        return repository.save(like);
    }

    @Transactional
    public void delete(Like like){repository.delete(like);}

}

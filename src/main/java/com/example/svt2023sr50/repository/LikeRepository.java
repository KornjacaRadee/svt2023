package com.example.svt2023sr50.repository;

import com.example.svt2023sr50.model.Comment;
import com.example.svt2023sr50.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}

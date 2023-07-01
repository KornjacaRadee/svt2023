package com.example.svt2023sr50.repository;

import com.example.svt2023sr50.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Override
    Group getOne(Long aLong);
}

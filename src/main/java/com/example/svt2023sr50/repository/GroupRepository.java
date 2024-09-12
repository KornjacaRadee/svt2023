package com.example.svt2023sr50.repository;

import com.example.svt2023sr50.indeexmodel.GroupIndex;
import com.example.svt2023sr50.model.Group;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Override
    Group getOne(Long aLong);
    List<Group> findByNameContainingOrDescripitonContaining(String name, String descripiton);

}

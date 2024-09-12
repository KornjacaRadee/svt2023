package com.example.svt2023sr50.repository;

import com.example.svt2023sr50.indeexmodel.DummyIndex;
import com.example.svt2023sr50.model.DummyTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DummyRepository extends JpaRepository<DummyTable, Integer> {




}

package com.example.svt2023sr50.services.interfaces;

import com.example.svt2023sr50.indeexmodel.DummyIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {

    Page<DummyIndex> simpleSearch(List<String> keywords, Pageable pageable);

    Page<DummyIndex> advancedSearch(List<String> expression, Pageable pageable);
}

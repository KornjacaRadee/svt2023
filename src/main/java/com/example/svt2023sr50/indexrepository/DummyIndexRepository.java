package com.example.svt2023sr50.indexrepository;

import com.example.svt2023sr50.indeexmodel.DummyIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DummyIndexRepository
        extends ElasticsearchRepository<DummyIndex, String> {
    List<DummyIndex> findByContentSrContainingOrTitleContaining(String contentSr,String title);
}

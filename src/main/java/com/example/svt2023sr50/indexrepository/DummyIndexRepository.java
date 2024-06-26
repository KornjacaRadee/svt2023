package com.example.svt2023sr50.indexrepository;

import com.example.svt2023sr50.indeexmodel.DummyIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyIndexRepository
        extends ElasticsearchRepository<DummyIndex, String> {
}

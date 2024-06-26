package com.example.svt2023sr50.services.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IndexingService {

    String indexDocument(MultipartFile documentFile);
}

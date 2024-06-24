package com.example.svt2023sr50;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinIOService {

    @Autowired
    private MinioClient minioClient;

    public String uploadFile(String bucketName, String objectName, InputStream stream, long size, String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                                    stream, size, -1)
                            .contentType(contentType)
                            .build());
            return objectName;
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occurred: " + e);
        }
    }

    // Implement methods for other MinIO operations if needed
}


package com.example.grpcserver.service;

import com.example.grpcserver.config.MinIoConfigProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class MinioService {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIoConfigProperties minIoConfigProperties;


    public String MinioUploadFile(MultipartFile file) throws Exception {
     if(!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minIoConfigProperties.getBucketName()).build())) {
         minioClient.makeBucket(MakeBucketArgs.builder().bucket(minIoConfigProperties.getBucketName()).build());
     }

     String objectName = UUID.randomUUID()+"-"+file.getOriginalFilename();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(minIoConfigProperties.getBucketName())
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());

        return objectName;
    }
}

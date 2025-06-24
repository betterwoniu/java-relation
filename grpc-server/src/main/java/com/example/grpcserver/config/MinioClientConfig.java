package com.example.grpcserver.config;

import io.minio.UploadObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

import java.io.IOException;

@Configuration
public class MinioClientConfig {

    @Autowired
    private MinIoConfigProperties minIoConfigProperties;
    @Bean
    public MinioClient minioClient() throws Exception {
       return MinioClient.builder()
                .endpoint(minIoConfigProperties.getServiceUrl())
                .credentials("root","kycx@123")
                .build();

    }
}

package com.example.grpcserver.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = MinIoConfigProperties.PREFIX)
public class MinIoConfigProperties {

    public static final String PREFIX ="minio";

    private String serviceUrl;
    private String accessKey;
    private String secretAccessKey;
    private String bucketName;

}

package com.example.grpcserver.controller;

import com.example.grpcserver.config.MinIoConfigProperties;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RequestMapping("/file")
@Controller
public class MinIOController {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIoConfigProperties minIoConfigProperties;


    @PostMapping("/upload")
    @ResponseBody
    public String UploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        if(!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minIoConfigProperties.getBucketName()).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minIoConfigProperties.getBucketName()).build());
        }
        String objectName = UUID.randomUUID()+"_"+file.getOriginalFilename();
        minioClient.putObject(PutObjectArgs.builder().bucket(minIoConfigProperties.getBucketName()).object(objectName).stream(file.getInputStream(),file.getSize(),-1).build());
        return objectName;
    }

    @GetMapping("/download/{objectName}")
    public ResponseEntity<InputStreamResource> download(@PathVariable String objectName) throws Exception {
        InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(minIoConfigProperties.getBucketName()).object(objectName).build());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }

    @GetMapping("/delete/{objectName}")
    public void delete(@PathVariable String objectName) throws Exception {
       minioClient.removeObject(RemoveObjectArgs.builder().bucket(minIoConfigProperties.getBucketName()).object(objectName).build());
    }

}

package com.example.grpcserver.controller;

import com.example.grpcserver.config.MinIoConfigProperties;
import io.minio.*;
import io.minio.errors.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "", tags = "文件管理")
@Controller
public class MinIOController {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIoConfigProperties minIoConfigProperties;

    @ApiOperation(value = "上传")
    @PostMapping("/upload")
    @ResponseBody
    public String UploadFile( @ApiParam(value = "上传的文件", required = true) @RequestPart("file") MultipartFile file) throws Exception {
        if(!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minIoConfigProperties.getBucketName()).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minIoConfigProperties.getBucketName()).build());
        }
        String objectName = UUID.randomUUID()+"_"+file.getOriginalFilename();
        minioClient.putObject(PutObjectArgs.builder().bucket(minIoConfigProperties.getBucketName()).object(objectName).stream(file.getInputStream(),file.getSize(),-1).build());
        return objectName;
    }

    @ApiOperation(value = "下载")
    @GetMapping("/download/{objectName}")
    public ResponseEntity<InputStreamResource> download(@PathVariable String objectName) throws Exception {
        InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(minIoConfigProperties.getBucketName()).object(objectName).build());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete/{objectName}")
    @ResponseBody
    public void delete(@PathVariable String objectName) throws Exception {
       minioClient.removeObject(RemoveObjectArgs.builder().bucket(minIoConfigProperties.getBucketName()).object(objectName).build());
    }


    @RequestMapping(path = "/miniotxt",method = RequestMethod.GET)
    @ResponseBody
    public String minioTxt(){
        return "minioTxt";
    }

}

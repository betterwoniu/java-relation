package com.example.basic;

import com.example.basic.entity.FileReviewDto;
import com.example.basic.entity.SensitivityResponseCommon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicApplicationTests {

    @Test
    void contextLoads() throws JsonProcessingException {


        String jstr = "{\n" +
                "    \"success\": true,\n" +
                "    \"message\": \"操作成功\",\n" +
                "    \"code\": 0,\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"id\": \"1970684542810451969\",\n" +
                "            \"fileName\": \"参展演示 -国科众安提供.docx\",\n" +
                "            \"fileType\": \"docx\",\n" +
                "            \"fileInfo\": \"30.89 KB\",\n" +
                "            \"charNumber\": 6918,\n" +
                "            \"riskNumber\": 103,\n" +
                "            \"reviewTime\": 73,\n" +
                "            \"reviewStatus\": 3,\n" +
                "            \"errorMsg\": null,\n" +
                "            \"reviewExtent\": 3,\n" +
                "            \"commentFileUrl\": \"2025/09/24/9d38ec35c1064219b1849080343318bd.docx\",\n" +
                "            \"revisionFileUrl\": \"2025/09/24/01614adb8d264e168a9f348156c4fc17.docx\",\n" +
                "            \"finishTime\": \"2025-09-24 11:01:14\",\n" +
                "            \"errorsUrl\": \"2025/09/24/8d1b199256dc4bb280876814a879244c.json\",\n" +
                "            \"errorStatisticJson\": \"{\\\"categoryTwoNameMap\\\":{\\\"港澳台敏感词\\\":3,\\\"党政固定表述\\\":5,\\\"落马官员\\\":6,\\\"涉黄、赌、毒、恐、暴\\\":2,\\\"句子查重\\\":2,\\\"国家部门名称\\\":2,\\\"字词错误\\\":60,\\\"重要讲话引用\\\":5,\\\"语病\\\":8,\\\"党政敏感词\\\":5,\\\"领导人姓名职务\\\":5},\\\"riskLevelMap\\\":{1:70,3:33},\\\"categoryOneNameMap\\\":{\\\"党政敏感\\\":31,\\\"通用敏感\\\":72}}\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();

        SensitivityResponseCommon<FileReviewDto> sensitivityResponseCommon =  objectMapper.readValue(jstr,SensitivityResponseCommon.class);

        System.out.println(sensitivityResponseCommon.toString());



    }

}

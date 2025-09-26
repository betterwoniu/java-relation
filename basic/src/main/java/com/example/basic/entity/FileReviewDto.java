package com.example.basic.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FileReviewDto implements Serializable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("fileType")
    private String fileType;

    @JsonProperty("fileInfo")
    private String fileInfo;

    @JsonProperty("charNumber")
    private Integer charNumber;

    @JsonProperty("riskNumber")
    private Integer riskNumber;

    @JsonProperty("fileUrl")
    private String fileUrl;

    @JsonProperty("reviewTime")
    private Integer reviewTime;

    @JsonProperty("reviewStatus")
    private Short reviewStatus;

    @JsonProperty("errorMsg")
    private String errorMsg;

    @JsonProperty("categoryIds")
    private String categoryIds;

    @JsonProperty("reviewExtent")
    private Integer reviewExtent;

    @JsonProperty("commentFileUrl")
    private String commentFileUrl;

    @JsonProperty("finishTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;

    @JsonProperty("reviewContent")
    private String reviewContent;

    @JsonProperty("errorStatisticJson")
    private String errorStatisticJson;

    private String msg;
}

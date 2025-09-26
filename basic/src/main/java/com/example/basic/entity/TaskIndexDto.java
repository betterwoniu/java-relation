package com.example.basic.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TaskIndexDto {

    private String id;
    private String message;
}

package com.example.basic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensitivityResponseCommon<T> implements Serializable {
    private static final long serialVersionUID = 3941113998180294985L;

    private Boolean success;

    private String message;

    private Integer code;

    private T data;
}

package com.example.basic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 2761211015093852257L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回的结果数据
     */
    private T data;


    public ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    /**
     * 返回成功200 没有返回信息
     * @return
     * @param <T>
     */
    public static <T> ResponseResult<T> success(){
        ResponseResult<T> result = new ResponseResult<T>();
        result.setCode(200);
        return result;
    }

    /**
     * 返回成功带信息
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setCode(200);
        result.setData(data);
        return result;
    }

    /**
     * 返回失败带信息
     * @param code
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseResult<T> error(Integer code,T data) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setCode(code);
        result.setData(data);
        return result;
    }
}

package com.tools;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private Integer code = null;
    private T data = null;
    private String msg = null;
    private Integer count = null;   //分页专用

    //分页专用
    public static <T> Result Table(String msg, Integer count, T data){
        return create(0,msg,count,data);
    }

    public static <T> Result Success(T data){
        return create(200,data);
    }

    public static Result Success(String msg){
        return create(200,msg);
    }

    public static <T> Result Success(T data, String msg){
        return create(200,data,msg);
    }

    public static Result Error(String msg){
        return create(0,msg);
    }

    //分页专用
    private static <T> Result create(Integer code, String msg, Integer count, T data){
        Result result = new Result();
        result.setCode(0);
        result.setMsg(msg);
        result.setCount(count);
        result.setData(data);
        return result;
    }

    private static <T> Result create(Integer code, T data){
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        return result;
    }

    private static Result create(Integer code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    private static <T> Result create(Integer code, T data, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

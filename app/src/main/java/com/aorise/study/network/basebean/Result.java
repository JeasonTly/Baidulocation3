package com.aorise.study.network.basebean;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/29.
 */
public class Result<T> {
    private String msg;
    private T data;
    private int id ;
    private int code ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

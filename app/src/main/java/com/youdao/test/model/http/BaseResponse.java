package com.youdao.test.model.http;

/**
 * Created by duchao on 2017/10/24.
 */

public class BaseResponse<T> {

    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 这里定义正常的返回code
    public boolean isOk() {
        return code == 0;
    }

}
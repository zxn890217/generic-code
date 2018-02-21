package com.my.gc.model;

/**
 * Created by zxn on 2018/1/4.
 */
public class ResponseResult {
    private boolean success;
    private String code;
    private String msg;
    private Object result;

    public ResponseResult(){}

    public ResponseResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public ResponseResult(boolean success, String msg, Object result) {
        this.success = success;
        this.msg = msg;
        this.result = result;
    }

    public ResponseResult(boolean success, String code, String msg, Object result) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

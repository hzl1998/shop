package com.hzl.result;

/**
 * @description 统一 API响应结果封装
 * @memo 控制Result权限，构建结果Result对象统一使用ResultFactory工厂类来创建
 */
public class Result {
    private int code;    //响应状态码
    private boolean error;
    private String msg;    //响应提示信息
    private Object data;    //响应结果对象

    Result(int code, boolean error, String msg, Object data) {
        this.code = code;
        this.error = error;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

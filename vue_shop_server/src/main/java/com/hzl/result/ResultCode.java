package com.hzl.result;

/**
 * @description 响应码枚举，参考 HTTP状态码的语义
 */
public enum  ResultCode {

    SUCCESS(200),    //成功

    FAIL(400),    //失败

    UNAUTHORIZED(401),    //未认证（签名错误）

    UNAUTHORIZATION(403),    //无权限

    NOT_FOUND(404),    //接口不存在

    INTERNAL_SERVER_ERROR(500);    //服务器内部错误

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
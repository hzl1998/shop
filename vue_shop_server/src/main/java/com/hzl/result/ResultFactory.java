package com.hzl.result;

/**
 *  @description 响应结果生成工厂类
 */
public class ResultFactory {

    public static Result buildSuccessResult(Object data) {
        return buildResult(com.cn.ncvt.result.ResultCode.SUCCESS, false, "请求成功", data);
    }

    public static Result buildFailResult(String message) {
        return buildResult(com.cn.ncvt.result.ResultCode.FAIL, true, message, null);
    }

    public static Result buildPermissionFailResult(String message) {
        return buildResult(com.cn.ncvt.result.ResultCode.UNAUTHORIZED, true, message, null);
    }

    public static Result buildResult(com.cn.ncvt.result.ResultCode resultCode, boolean error, String message, Object data) {
        return buildResult(resultCode.code, error, message, data);
    }

    public static Result buildResult(int resultCode, boolean error, String message, Object data) {
        return new Result(resultCode, error, message, data);
    }
}

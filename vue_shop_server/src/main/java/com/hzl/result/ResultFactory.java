package com.hzl.result;

/**
 *  @description 响应结果生成工厂类
 */
public class ResultFactory {

    public static Result buildSuccessResult(Object data,String msg) {
        return buildResult(ResultCode.SUCCESS, false, msg, data);
    }

    public static Result buildFailResult(String msg) {
        return buildResult(ResultCode.FAIL, true, msg, null);
    }

    public static Result buildPermissionFailResult(String msg) {
        return buildResult(ResultCode.UNAUTHORIZED, true, msg, null);
    }

    public static Result buildResult(ResultCode resultCode, boolean error, String msg, Object data) {
        return buildResult(resultCode.code, error, msg, data);
    }

    public static Result buildResult(int resultCode, boolean error, String msg, Object data) {
        return new Result(resultCode, error, msg, data);
    }
}

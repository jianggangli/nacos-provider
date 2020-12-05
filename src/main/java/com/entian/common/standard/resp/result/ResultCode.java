package com.entian.common.standard.resp.result;

import lombok.Getter;

/**
 * @author jianggangli
 * @description 响应码枚举
 */
@Getter
public enum ResultCode {
    /**
     * 数值在区间说明
     * 20000开始:请求成功
     * 30000开始:要完成请求,条件还不满足
     * 40000开始:客户端错误
     * 50000开始:服务端错误
     */
    SUCCESS(20000, "success", "操作成功"),

    FAILED(30000, "condition discontent", "响应失败"),

    VALIDATE_FAILED(40000, "client error", "参数校验失败"),

    ERROR(50000, "unknown server error", "内部错误");

    private int code;
    private String msg;
    private String detail;

    ResultCode(int code, String msg, String detail) {
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }

}

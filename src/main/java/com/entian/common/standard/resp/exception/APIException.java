package com.entian.common.standard.resp.exception;

import lombok.Getter;

/**
 * @author jianggangli
 * @description 自定义异常
 */
@Getter //只要getter方法，无需setter
public class APIException extends RuntimeException {
    private int code;
    private String msg;
    private String detail;

    public APIException() {
        this(50000, "接口错误", "内部发生异常");
    }

    public APIException(String msg, String detail) {
        this(50000, msg, detail);
    }

    public APIException(int code, String msg, String detail) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }
}

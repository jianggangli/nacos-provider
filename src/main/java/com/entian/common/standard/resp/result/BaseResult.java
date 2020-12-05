package com.entian.common.standard.resp.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jianggangli
 * @description 自定义统一响应体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("基础属性返回包装类")
public class BaseResult<T> {
    @ApiModelProperty(value = "状态码", notes = "默认20000是成功", example = "20000", allowableValues = "20000,30000,40000...")
    private int code;
    @ApiModelProperty(value = "响应信息", notes = "来说明响应情况", example = "操作成功", allowableValues = "操作成功,响应失败,参数校验失败...")
    private String msg;
    @ApiModelProperty(value = "响应信息", notes = "具体错误细节", example = "获取数据成功", allowableValues = "人员不存在,表不存在...")
    private String detail;
    @ApiModelProperty(value = "返回内容data,泛型", example = "user", allowableValues = "返回对象")
    private T data;

    public BaseResult(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public BaseResult(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.detail = resultCode.getDetail();
        this.data = data;
    }


    public static <T> BaseResult<T> ok(T data) {
        return BaseResult.<T>builder().code(ResultCode.SUCCESS.getCode())
                .msg(ResultCode.SUCCESS.getMsg())
                .detail(ResultCode.SUCCESS.getDetail())
                .data(data)
                .build();
    }

    public static <T> BaseResult<T> ok() {
        return ok(null);
    }

    public static <T> BaseResult<T> fail(ResultCode resultCode, String errorDetail) {
        return BaseResult.<T>builder().code(resultCode.getCode())
                .msg(resultCode.getMsg())
                .detail(errorDetail)
                .build();
    }

    public static <T> BaseResult<T> warn(int resultCode, String msg, String errorDetail) {
        return BaseResult.<T>builder().code(resultCode)
                .msg(msg)
                .detail(errorDetail)
                .build();
    }

    public static <T> BaseResult<T> fail(String errorDetail) {
        return fail(ResultCode.FAILED, errorDetail);
    }


}

package com.entian.common.standard.resp.config;

import com.entian.common.standard.resp.exception.APIException;
import com.entian.common.standard.resp.result.BaseResult;
import com.entian.common.standard.resp.result.ResultCode;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Field;
import java.util.stream.Collectors;

/**
 * @author jianggangli
 * @description 全局异常处理
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    public BaseResult<String> apiExceptionHandler(APIException e) {
        return BaseResult.warn(ResultCode.FAILED.getCode(), e.getMsg(), e.getDetail());
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public BaseResult<String> bindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining());
        return BaseResult.fail(ResultCode.VALIDATE_FAILED, message);
    }

    /**
     * 处理请求参数格式错误
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResult<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());
        return BaseResult.fail(ResultCode.VALIDATE_FAILED, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) throws NoSuchFieldException {
        // 从异常对象中拿到错误信息
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        // 参数的Class对象，等下好通过字段名称获取Field对象
        Class<?> parameterType = e.getParameter().getParameterType();
        // 拿到错误的字段名称
        String fieldName = e.getBindingResult().getFieldError().getField();
        Field field = parameterType.getDeclaredField(fieldName);

        // 没有注解就提取错误提示信息进行返回统一错误码
        return  BaseResult.fail(ResultCode.VALIDATE_FAILED, defaultMessage);
    }
    /**
     * @param exception 所有异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    public BaseResult<String> handleException(Exception exception) {
        exception.printStackTrace();
        String defaultMessage = exception.getMessage();
        return BaseResult.fail(ResultCode.ERROR, defaultMessage);
    }
}

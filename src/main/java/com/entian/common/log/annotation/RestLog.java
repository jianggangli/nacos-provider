package com.entian.common.log.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * 
 * @author jianggangli
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestLog
{
    /**
     * 模块
     */
     String module() default "";

    /**
     * 是否保存请求的参数
     */
     boolean isSaveRequestData() default true;
}

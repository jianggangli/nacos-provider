package com.entian.common.standard.resp.config;

import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author jianggangli
 * @version 1.0 2020/12/1 16:51
 * 功能:
 */
@RestControllerAdvice(basePackages = {"com.entian"})
public class RequestControllerAdvice implements RequestBodyAdvice {

    /**
     *  设置条件,这个条件为true才会执行下面的beforeBodyRead方法
     * @param methodParameter
     * @param type
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 这里进行解密
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass){

        return new HttpInputMessage() {
            @Override
            public InputStream getBody() throws IOException {
                //此处进行解密数据
                return new ByteArrayInputStream(
                        IOUtils.toString(httpInputMessage.getBody())
                                .getBytes(StandardCharsets.UTF_8)
                );
            }

            @Override
            public HttpHeaders getHeaders() {
                return httpInputMessage.getHeaders();
            }
        };
    }


    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    /**
     * 传入的json是空值的时候,进入这个方法
     * @param o
     * @param httpInputMessage
     * @param methodParameter
     * @param type
     * @param aClass
     * @return
     */
    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}

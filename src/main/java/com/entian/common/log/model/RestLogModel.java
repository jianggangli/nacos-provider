package com.entian.common.log.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author jianggangli
 * @version 1.0 2020/11/30 19:34
 * 功能:
 */
@Data
public class RestLogModel implements Serializable {
    private static final long serialVersionUID = 826080434737985892L;

    /**
     * 服务名
     */
    private String service;

    /**
     * 模块名
     */
    private String module;

    /**
     * 客户端ip
     */
    private String clientIp;

    /**
     * 请求映射路径
     */
    private String requestUri;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 方法名
     */
    private String method;

    /**
     * 参数类型
     */
    private List<String> paramType;

    /**
     * 参数名称
     */
    private List<String> paramName;

    /**
     * 参数值
     */
    private List<String> paramValue;

    /**
     * 操作人姓名
     */
    private String user;

    /**
     * 接口返回数据
     */
    private String baseResult;

    /**
     * 接口请求时间
     */
    private String startTime;

    /**
     * 接口返回时间
     */
    private String endTime;

    /**
     * 总消耗时间
     */
    private int spendTime;

    /**
     * error,success
     */
    private String status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 错误消息
     */
    private String errorType;

}

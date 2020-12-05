package com.entian.common.log.service;

import com.alibaba.fastjson.JSON;
import com.entian.common.log.model.RestLogModel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 * 
 * @author jianggangli
 */
@Service
public class AsyncLogService
{


    /**
     * 保存系统日志记录
     */
    @Async
    public void saveLogModel(RestLogModel restLogModel)
    {
        System.out.println(JSON.toJSONString(restLogModel));
    }
}

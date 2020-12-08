package com.entian.common.mybatis.sql.log;

import cn.hutool.core.collection.CollUtil;
import com.entian.common.mybatis.sql.annotation.DataLog;
import com.entian.common.mybatis.sql.handle.BaseDataLog;
import com.entian.common.mybatis.sql.handle.LogData;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 数据日志处理
 * </p>
 *
 * @author jianggangli
 * @since 2020/8/11
 */
@Component
public class DataLogHandle extends BaseDataLog {

    @Override
    public void setting() {

    }

    @Override
    public void change(DataLog dataLog, LogData data) {
        if (CollUtil.isEmpty(data.getDataChanges())) {
            return;
        }
        // 存库
        System.err.println("存库成功：" + data.getLogStr());
    }

}

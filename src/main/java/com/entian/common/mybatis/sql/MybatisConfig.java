package com.entian.common.mybatis.sql;

import com.entian.common.mybatis.sql.handle.BaseDataLog;
import com.entian.common.mybatis.sql.handle.DataUpdateInterceptor;
import com.entian.common.mybatis.sql.interceptor.PerformanceInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * Mybatis-Plus配置
 * </p>
 *
 * @author jianggangli
 * @since 2020/5/7
 */
@Configuration
@EnableTransactionManagement
//@MapperScan("com.lith.**.mapper")
public class MybatisConfig {

    /**
     * <p>
     * SQL执行效率插件  设置 dev test 环境开启
     * </p>
     *
     * @return cn.rc100.common.data.mybatis.EplusPerformanceInterceptor
     * @author jianggangli
     * @since 2020/3/11
     */
    @Bean
//    @Profile({"dev","test"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * <p>
     * 数据更新操作处理
     * </p>
     *
     * @return com.entian.common.mybatis.sql.handle.DataUpdateInterceptor
     * @author jianggangli
     * @since 2020/5/11
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(BaseDataLog.class)
    public DataUpdateInterceptor dataUpdateInterceptor() {
        return new DataUpdateInterceptor();
    }
}

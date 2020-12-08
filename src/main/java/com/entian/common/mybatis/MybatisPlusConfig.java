package com.entian.common.mybatis;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;

/**
 * @author jianggangli
 * @version 1.0 2020/12/7 10:45
 * 功能:
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        return new PaginationInnerInterceptor();
    }

    @Bean
    public MybatisPlusInterceptor optimisticLockerInnerInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    public MybatisPlusInterceptor blockAttackInnerInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    @Bean
    public MySqlInjector sqlInjector() {
        return new MySqlInjector();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        HashMap<String, TableNameHandler> tableNameHandlerMap = new HashMap<String, TableNameHandler>(2);

        tableNameHandlerMap.put("user", new TableNameHandler() {
            /**
             * 生成动态表名
             *
             * @param sql       当前执行 SQL
             * @param tableName 表名
             * @return String
             */
            @Override
            public String dynamicTableName(String sql, String tableName) {
                //                String year = "_2018";
                //                int random = new Random().nextInt(10);
                //                if (random % 2 == 1) {
                //                    year = "_2019";
                //                }
                //                return tableName + year;
                return tableName;
            }

        });
        //        tableNameHandlerMap.put("t_a_dc_check_xxx2", new TableNameHandler() {
        //            /**
        //             * 生成动态表名
        //             *
        //             * @param sql       当前执行 SQL
        //             * @param tableName 表名
        //             * @return String
        //             */
        //            @Override
        //            public String dynamicTableName(String sql, String tableName) {
        //                // 自定义表名规则，或者从配置文件、request上下文中读取
        //                // 假设这里的用户表根据年份来进行分表操作
        //                Date date = new Date();
        //                //DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(LocalDateTime.now());
        //                String year = String.format("_%tY%tm", date,date);
        //                // 返回最后需要操作的表名，sys_user_2019
        //                return "t_a_dc_check_xxx2" + year;
        //            }
        //        });

        dynamicTableNameInnerInterceptor.setTableNameHandlerMap(tableNameHandlerMap);
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        return interceptor;
    }

}

package com.entian.common.mybatis.sql.handle;

import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * <p>
 * 数据变化
 * </p>
 *
 * @author jianggangli
 * @since 2020/7/27
 */
@Data
public class DataChange {
    /**
     * sqlSessionFactory
     */
    private SqlSessionFactory sqlSessionFactory;
    /**
     * sqlStatement
     */
    private String sqlStatement;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 对应实体类
     */
    private Class<?> entityType;
    /**
     * 更新前数据
     */
    private List<?> oldData;
    /**
     * 更新后数据
     */
    private List<?> newData;
}

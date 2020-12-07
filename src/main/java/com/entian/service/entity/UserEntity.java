package com.entian.service.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.entian.common.mybatis.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author jianggangli
 * @version 1.0 2020/12/7 11:21
 * 功能:
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user")
public class UserEntity extends BaseEntity {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 技能
     */
    private String skill;
    /**
     * 评价
     */
    private String evaluate;
    /**
     * 分数
     */
    private Long fraction;
}

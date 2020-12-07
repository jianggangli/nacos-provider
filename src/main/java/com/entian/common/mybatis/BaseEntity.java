package com.entian.common.mybatis;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author jianggangli
 * @version 1.0 2020/12/7 10:48
 * 功能:
 */
@Data
public abstract class BaseEntity {

    @TableId(type = IdType.ASSIGN_UUID)
    protected String id;
    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;
    @TableField(fill = FieldFill.INSERT)
    protected String createBy;
    @TableField(fill = FieldFill.UPDATE)
    protected Date updateTime;
    @TableField(fill = FieldFill.UPDATE)
    protected String updateBy;

    @TableLogic
    @TableField(fill = FieldFill.UPDATE)
    protected Boolean deleted=false;

    @Version
    protected Integer version;

}

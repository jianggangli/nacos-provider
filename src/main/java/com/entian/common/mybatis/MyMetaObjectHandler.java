package com.entian.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.entian.common.standard.session.SessionContextFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author jianggangli
 * @version 1.0 2020/12/7 14:47
 * 功能:
 */
@Slf4j
@Component
public class MyMetaObjectHandler  implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "deleted", Boolean.class, false);
        this.strictInsertFill(metaObject, "version", Integer.class, 1);
        this.strictInsertFill(metaObject, "createBy", String.class, SessionContextFacade.getSessionContext().getUserId());
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateBy", String.class, SessionContextFacade.getSessionContext().getUserId());
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}


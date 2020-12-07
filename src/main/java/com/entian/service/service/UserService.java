package com.entian.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entian.service.entity.UserEntity;

/**
 * @author jianggangli
 * @version 1.0 2020/12/7 13:28
 * 功能:
 */
public interface UserService extends IService<UserEntity> {
    void deleteByIdWithFill(UserEntity userEntity);
}

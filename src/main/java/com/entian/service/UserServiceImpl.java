package com.entian.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entian.service.entity.UserEntity;
import com.entian.service.mapper.UserMapper;
import com.entian.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jianggangli
 * @version 1.0 2020/12/7 11:28
 * 功能:
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void deleteByIdWithFill(UserEntity userEntity) {

        userMapper.deleteByIdWithFill(userEntity);
    }
}

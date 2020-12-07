package com.entian.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jianggangli
 * @description 用户实体类
 */
@Data
@ApiModel("用户")
public class UserDto {
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户邮箱")
    private String email;
}

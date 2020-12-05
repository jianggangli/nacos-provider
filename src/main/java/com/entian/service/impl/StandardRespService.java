package com.entian.service.impl;

import com.entian.common.standard.resp.annotation.NotResponseBody;
import com.entian.common.standard.resp.result.BaseResult;
import com.entian.service.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jianggangli
 * @description 接口返回统一规范
 */
@RestController
@Api(tags = "接口返回统一规范")
@RequestMapping("/standard")
public class StandardRespService {

    @ApiOperation("获得单个用户")
    @GetMapping("/getNotResponseBody")
    @NotResponseBody
    public UserDto getNotResponseBody() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setAccount("12345678");
        userDto.setPassword("12345678");
        userDto.setEmail("123@qq.com");
        return userDto;
    }

    @ApiOperation("获得单个用户")
    @GetMapping("/getResponseBody")
    public BaseResult<UserDto> getResponseBody() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setAccount("12345678");
        userDto.setPassword("12345678");
        userDto.setEmail("123@qq.com");
        return BaseResult.ok(userDto);
    }


}

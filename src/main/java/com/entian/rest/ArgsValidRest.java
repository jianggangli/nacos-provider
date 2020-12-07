package com.entian.rest;

import com.entian.common.standard.resp.result.BaseResult;
import com.entian.common.standard.session.SessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author jianggangli
 * @version 1.0 2020/7/1 9:48
 * 功能:
 */
@Slf4j
@RestController
@RefreshScope
@Api(tags = "入参校验@Validated")
@Validated
public class ArgsValidRest {

    @Value("${useLocalCache}")
    private String useLocalCache;

    @Autowired
    private SessionContext sessionContext;


    @ApiOperation("入参校验例子")
    @GetMapping("/get-args-valid")
    public BaseResult<String> getArgsValid
            (@NotNull(message = "用户名不能空") String username,
            @NotNull(message = "密码不能为空") String password
            ) {
        log.info(sessionContext.toString());
        return BaseResult.ok("具体成功返回对象信息");
    }


}

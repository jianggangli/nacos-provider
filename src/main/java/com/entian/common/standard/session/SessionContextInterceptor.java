package com.entian.common.standard.session;

import com.entian.common.standard.resp.exception.APIException;
import com.entian.common.standard.session.jwt.JwtHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jianggangli
 * @version 1.0 2020/12/4 13:57
 * 功能:
 */
@Slf4j
@Component
public class SessionContextInterceptor implements HandlerInterceptor {

    @Autowired
    JwtHandler jwtHandler;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 解析token
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) {
        String token = request.getHeader(JwtHandler.TOKEN_HEADER);
        if (org.springframework.util.StringUtils.isEmpty(token)) {
            throw new APIException("token为空", "token不能为空,请先登入");
        }
        String content = jwtHandler.parseToken(token);
        SessionContext sessionContext = null;
        try {
             sessionContext = objectMapper.readValue(content, SessionContextModel.class);
            sessionContext.setToken(token);
        } catch (IOException exception) {
            exception.printStackTrace();
            log.error("收到非法token:{}", content);

        }

        SessionContextFacade.setSessionContext(sessionContext);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
        SessionContextFacade.removeSessionContext();
    }

}

package com.entian.common.standard.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jianggangli
 * @version 1.0 2020/12/4 13:56
 * 功能:
 */
@Slf4j
@Configuration
public class SessionContextFacade implements WebMvcConfigurer {

    @Autowired
    private SessionContextInterceptor sessionContextInterceptor;

    private static final ThreadLocal<SessionContext> SESSION_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 拦截范围
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionContextInterceptor).addPathPatterns("/**");
    }

    public static SessionContext getSessionContext() {
        SessionContext sessionContext = SESSION_CONTEXT_THREAD_LOCAL.get();
        return sessionContext == null ? new SessionContextModel() : sessionContext;
    }

    public static void setSessionContext(SessionContext sessionContext) {
        SESSION_CONTEXT_THREAD_LOCAL.set(sessionContext);
    }

    public static void removeSessionContext() {
        SESSION_CONTEXT_THREAD_LOCAL.remove();
    }
}

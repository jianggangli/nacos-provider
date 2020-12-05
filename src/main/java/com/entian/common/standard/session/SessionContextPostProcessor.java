package com.entian.common.standard.session;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author jianggangli
 * @version 1.0 2020/12/4 14:00
 * 功能:
 */
@Component
public class SessionContextPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.registerResolvableDependency(SessionContext.class, new RequestContextObjectFactory());
    }

    public static class RequestContextObjectFactory implements ObjectFactory<SessionContext>, Serializable {

        private static final long serialVersionUID = -1262793416232102902L;

        @Override
        public SessionContext getObject() {
            return SessionContextFacade.getSessionContext();
        }
        @Override
        public String toString() {
            return SessionContextFacade.getSessionContext().toString();
        }
    }

}

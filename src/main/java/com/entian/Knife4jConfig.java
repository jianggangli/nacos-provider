package com.entian;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created on 2020/5/7.
 *
 * @author jianggangli
 */


@EnableSwagger2
@EnableKnife4j
@Import(value = BeanValidatorPluginsConfiguration.class)
@Configuration
public class Knife4jConfig {
    @Bean(value = "serviceApiDemo")
    public Docket serviceApiDemo() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                //分组名称
                .groupName("SERVICE服务提供方")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.entian.service"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("SERVICE服务提供方 APIs").version("1.0").build();
    }

    /**
     * 还可以定义另外一个table
     */
}

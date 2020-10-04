package com.luoyu.knife4j.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @version 1.0
 * @author luoyu
 * @date 2019-08-09
 * @description Knife4j配置
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfig {

    /**
     * 定义分隔符
     */
    private static final String SPLITOR = ";";

    /**
     * @author luoyu
     * @description 配置token，以及设置扫描包的路径
     * @return Docket
     */
    @Bean("createRestApi1")
    public Docket createRestApi1() {
        //设置header里面的token
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token").description("token令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .groupName("1.0.0 版本")
                .select()
                //此处添加需要扫描接口的包路径
                .apis(basePackage("com.luoyu.knife4j.controller.test1" + SPLITOR
                        + "com.luoyu.knife4j.controller.test2" + SPLITOR ))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @author luoyu
     * @description 配置token，以及设置扫描包的路径
     * @return Docket
     */
    @Bean("createRestApi2")
    public Docket createRestApi2() {
        //设置header里面的token
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .groupName("2.0.0 版本")
                .select()
                //此处添加需要扫描接口的包路径
                .apis(basePackage("com.luoyu.knife4j.controller.test3" + SPLITOR ))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @author luoyu
     * @description 配置Knife4j页面显示内容
     * @return Docket
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Knife4j 接口文档")
                .description("Knife4j 接口文档")
                .version("1.0.0")
                .termsOfServiceUrl("http://localhost:8088/doc.html")
                .contact("luoyu")
                .build();
    }

    /**
     * @author luoyu
     * @description 重写basePackage方法，使能够实现多包访问
     * @param basePackage 所有包路径
     * @return Predicate<RequestHandler>
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).map(handlerPackage(basePackage)::apply).orElse(true);
    }

    /**
     * @author luoyu
     * @description 重写basePackage方法，使能够实现多包访问
     * @param basePackage 所有包路径
     * @return Function<Class<?>, Boolean>
     */
    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(SPLITOR)) {
                assert input != null;
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * @author luoyu
     * @description 重写basePackage方法，使能够实现多包访问
     * @param input
     * @return Optional<? extends Class<?>>
     */
    private static Optional<Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }

}

package com.luoyu.swagger.config;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * @author jinhaoxun
 * @date 2019-08-09
 * @description Swagger配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 定义分隔符
     */
    private static final String SPLITOR = ";";

    /**
     * @author jinhaoxun
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
                .apis(basePackage("com.luoyu.swagger.controller.test1" + SPLITOR
                        + "com.luoyu.swagger.controller.test2" + SPLITOR ))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @author jinhaoxun
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
                .apis(basePackage("com.luoyu.swagger.controller.test3" + SPLITOR ))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @author jinhaoxun
     * @description 配置Swagger2页面显示内容
     * @return Docket
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger 接口文档")
                .description("Swagger 接口文档")
                .version("1.0.0")
                .termsOfServiceUrl("http://localhost:8081/swagger-ui.html")
                .contact("luoyu")
                .build();
    }

    /**
     * @author jinhaoxun
     * @description 重写basePackage方法，使能够实现多包访问
     * @param basePackage 所有包路径
     * @return Predicate<RequestHandler>
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).map(handlerPackage(basePackage)::apply).orElse(true);
    }

    /**
     * @author jinhaoxun
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
     * @author jinhaoxun
     * @description 重写basePackage方法，使能够实现多包访问
     * @param input
     * @return Optional<? extends Class<?>>
     */
    private static Optional<Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }

}

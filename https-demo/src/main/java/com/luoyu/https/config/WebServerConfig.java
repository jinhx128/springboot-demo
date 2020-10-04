package com.luoyu.https.config;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.servlet.api.SecurityConstraint;
import io.undertow.servlet.api.SecurityInfo;
import io.undertow.servlet.api.TransportGuaranteeType;
import io.undertow.servlet.api.WebResourceCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 采用Undertow作为服务器，支持Https服务配置
 * @Author: luoyu
 * @Date: 2020/7/16 10:39 下午
 * @Version: 1.0.0
 */
@Configuration
public class WebServerConfig {

    /**
     * http服务端口
     */
    @Value("${custom.server.http.port}")
    private Integer httpPort;

    /**
     * https服务端口
     */
    @Value("${server.port}")
    private Integer httpsPort;

    /**
     * @author jinhaoxun
     * @description 采用Undertow作为服务器。
     * Undertow是一个用java编写的、灵活的、高性能的Web服务器，提供基于NIO的阻塞和非阻塞API，特点：
     * 非常轻量级，Undertow核心瓶子在1Mb以下。它在运行时也是轻量级的，有一个简单的嵌入式服务器使用少于4Mb的堆空间。
     * 支持HTTP升级，允许多个协议通过HTTP端口进行多路复用。
     * 提供对Web套接字的全面支持，包括JSR-356支持。
     * 提供对Servlet 3.1的支持，包括对嵌入式servlet的支持。还可以在同一部署中混合Servlet和本机Undertow非阻塞处理程序。
     * 可以嵌入在应用程序中或独立运行，只需几行代码。
     * 通过将处理程序链接在一起来配置Undertow服务器。它可以对各种功能进行配置，方便灵活。
     * @return ServletWebServerFactory
     */
    @Bean
    public ServletWebServerFactory undertowFactory() {
        UndertowServletWebServerFactory undertowFactory = new UndertowServletWebServerFactory();
        undertowFactory.addBuilderCustomizers((Undertow.Builder builder) -> {
            builder.addHttpListener(httpPort, "0.0.0.0");
            // 开启 HTTP2
            builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true);
        });
        undertowFactory.addDeploymentInfoCustomizers(deploymentInfo -> {
            // 开启 HTTP 自动跳转至 HTTPS
            deploymentInfo.addSecurityConstraint(new SecurityConstraint()
                    .addWebResourceCollection(new WebResourceCollection().addUrlPattern("/*"))
                    .setTransportGuaranteeType(TransportGuaranteeType.CONFIDENTIAL)
                    .setEmptyRoleSemantic(SecurityInfo.EmptyRoleSemantic.PERMIT))
                    .setConfidentialPortManager(exchange -> httpsPort);
        });
        return undertowFactory;
    }

}

package com.luoyu.dynamicmultipledatasources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DynamicMultipleDataSourcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicMultipleDataSourcesApplication.class, args);
    }

}

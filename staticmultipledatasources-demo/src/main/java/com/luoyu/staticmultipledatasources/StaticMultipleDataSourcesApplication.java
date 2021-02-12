package com.luoyu.staticmultipledatasources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StaticMultipleDataSourcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaticMultipleDataSourcesApplication.class, args);
    }

}

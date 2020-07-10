package com.luoyu.multipledatasources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MultipledatasourcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipledatasourcesApplication.class, args);
    }

}

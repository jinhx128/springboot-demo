package com.jinhaoxun.mpmultipledatasources;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.jinhaoxun.mpmultipledatasourcesdemo.mapper.db1","com.jinhaoxun.mpmultipledatasourcesdemo.mapper.db2"})
@SpringBootApplication
public class MpmultipledatasourcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpmultipledatasourcesApplication.class, args);
    }

}

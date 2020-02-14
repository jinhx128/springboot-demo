package com.jinhaoxun.mybatisplusdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.jinhaoxun.mybatisplusdemo.mapper")
@SpringBootApplication
public class MybatisplusDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisplusDemoApplication.class, args);
	}

}

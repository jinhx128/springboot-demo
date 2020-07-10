package com.luoyu.threadpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ThreadpoolDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadpoolDemoApplication.class, args);
    }

}

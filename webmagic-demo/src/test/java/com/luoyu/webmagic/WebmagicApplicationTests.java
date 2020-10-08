package com.luoyu.webmagic;

import com.luoyu.webmagic.fixbug.HttpClientDownloader;
import com.luoyu.webmagic.pageprocessor.CustomPageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Spider;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class WebmagicApplicationTests {

    @Test
    void test(){
        Spider.create(new CustomPageProcessor())
                .setDownloader(new HttpClientDownloader())
                //从"https://github.com/code4craft"开始抓
                .addUrl("https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1602125812009_R&pv=&ic=&nc=1&z=&hd=&latest=&copyright=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&sid=&word=%E5%88%80%E5%89%91%E7%A5%9E%E5%9F%9F")
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }

    @BeforeEach
    void testBefore(){
        log.info("测试开始!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @AfterEach
    void testAfter(){
        log.info("测试结束!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}

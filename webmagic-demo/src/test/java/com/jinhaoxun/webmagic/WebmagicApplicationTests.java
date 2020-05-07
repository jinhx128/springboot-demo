package com.jinhaoxun.webmagic;

import com.jinhaoxun.webmagic.fixbug.HttpClientDownloader;
import com.jinhaoxun.webmagic.pageprocessor.CustomPageProcessor;
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
                .addUrl("https://search.51job.com/list/090200,000000,0000,38,9,99,JAVA,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=")
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

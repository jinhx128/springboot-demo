package com.luoyu.filetransmission;

import com.luoyu.filetransmission.util.sftputil.SftpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Vector;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class FileTransmissionApplicationTests {

    @Test
    void upLoadFileTest() throws Exception {
        File file = new File("E:\\2.xlsx");
        InputStream inputStream = new FileInputStream(file);

        SftpUtil.uploadFile("", "", "", 22, "/usr/local",
                "/testfile/", "test.xlsx", null, inputStream);
    }

    @Test
    void downLoadFileTest() throws Exception {
        SftpUtil.downloadFile("", "", "", 22,null,
                "/usr/local/testfile/", "test.csv","/Users/ao/Desktop/test.csv");
    }

    @Test
    void deleteFileTest() throws Exception {
        SftpUtil.deleteFile("", "", "", 22,null,
                "/usr/local/testfile/", "test.xlsx");
    }

    @Test
    void getFileListTest() throws Exception {
        Vector<?> fileList = SftpUtil.getFileList("", "", "",
                22, null,"/usr/local/testfile/");
        log.info(fileList.toString());
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

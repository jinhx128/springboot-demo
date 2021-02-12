//package com.luoyu.fileoperation.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//
//@Slf4j
//@Component
//public class FileUtils {
//
//    File dirFile = new File("/xxx/xxx.txt");
//    if (!dirFile.ex()) {
//        //判断目录是否存在，不存在创建
//        dirFile.mkdir();
//    }
//
//    File file = new File("/history/user-history-data.log");
//        if (!file.exists()) {
//        //判断文件是否存在，不存在创建
//        file.createNewFile();
//    }
//
//    //new FileWriter(path + "config.log", true)  设置true 在不覆盖以前文件的基础上继续写
//    BufferedWriter writer = new BufferedWriter(new FileWriter(sensorsFilePath + "/history/user-history-data.log", true));
//        try {
//        //写入文件
//        writer.write(JacksonUtil.objectToJson(profiles) + "\r\n");
//        //清空缓冲区数据
//        writer.flush();
//    } catch (Exception e) {
//        log.error("初始化神策用户数据，数据写入失败：" + sensorsApiRequest.getUserId() + "，msg：" + e);
//    }finally {
//        //关闭读写流
//        writer.close();
//    }
//
//}

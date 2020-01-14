package com.jinhaoxun.easyexceldemo.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.jinhaoxun.easyexceldemo.listener.ExcelListener;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/1/14 15:52
 * @Version: 1.0
 */
@Slf4j
public class ExcelUtils {

    /**
     * 解析excel文件内容
     *
     * @param fileName
     * @return
     */
    public static List<List<Object>> readExcel(String fileName) {

        File file = new File(fileName);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 解析每行结果在listener中处理
        ExcelListener listener = new ExcelListener();
        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
        excelReader.read();
        List<List<Object>> datas = listener.getDatas();
        return datas;
    }



    /**
     * 导出方法,生成excle
     *
     * @param filePath 绝对路径,
     * @param data     数据源
     * @param sheet    excle页面样式
     */
    public static void writeSimpleBySheet(String filePath, List<List<Object>> data, Sheet sheet) {

        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            writer.write1(data, sheet);
        } catch (FileNotFoundException e) {
            log.error("找不到文件或文件路径错误, 文件：{}", filePath);
        } finally {
            try {
                if (writer != null) {
                    writer.finish();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error("excel文件导出失败, 失败原因：{}", e);
            }
        }
    }
}

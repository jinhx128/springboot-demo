package com.luoyu.easyexcel;

import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.luoyu.easyexcel.entity.ExcelModel;
import com.luoyu.easyexcel.entity.ExcelModel1;
import com.luoyu.easyexcel.entity.ExcelModel2;
import com.luoyu.easyexcel.util.DataConvertUtil;
import com.luoyu.easyexcel.util.ExcelConvertCsvUtil;
import com.luoyu.easyexcel.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class EasyexcelApplicationTests {

    @Test
    void readExcelTest() throws Exception {
        //读取excel
        File file = new File("E:\\2.xlsx");
        InputStream inputStream = new FileInputStream(file);
        //导入excle
        List<ExcelModel> datas = ExcelUtil.readExcel(inputStream, ExcelModel.class, ExcelTypeEnum.XLSX);
        log.info(datas.toString());
    }

    @Test
    void writeExcelTest() throws Exception {
        //单sheet,单table导出测试
        List<ExcelModel> excelModelList = new ArrayList<ExcelModel>();
        for (int i = 0;i<5;i++){
            ExcelModel excelModel = new ExcelModel("日期"+i, "上班时间" + i,
                    "下班时间" + i, "加班时长" + i, "备注" + i);
            excelModelList.add(excelModel);
        }
        File file1 = new File("E:\\2.xlsx");
        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        byte[] bytes = ExcelUtil.writeExcel(outputStream1, excelModelList, ExcelModel.class, ExcelTypeEnum.XLSX);
        FileOutputStream outputStream = new FileOutputStream(file1);
        outputStream.write(bytes);
    }

    @Test
    void writeExcelTest1() throws Exception {
        //多sheet，多table导出测试，数据集制作
        List<ExcelModel> excelModelList = new ArrayList<ExcelModel>();
        List<ExcelModel1> excelModel1List = new ArrayList<ExcelModel1>();
        List<ExcelModel2> excelModel2List = new ArrayList<ExcelModel2>();
        for (int i = 0;i<5;i++){
            ExcelModel excelModel = new ExcelModel("日期"+i, "上班时间" + i,
                    "下班时间" + i, "加班时长" + i, "备注" + i);
            ExcelModel1 excelModel1 = new ExcelModel1("日期"+i, "上班时间" + i,
                    "下班时间" + i, "加班时长" + i, "备注" + i);
            ExcelModel2 excelModel2 = new ExcelModel2("日期"+i, "上班时间" + i,
                    "下班时间" + i, "加班时长" + i, "备注" + i);

            excelModelList.add(excelModel);
            excelModel1List.add(excelModel1);
            excelModel2List.add(excelModel2);
        }
        Map<String,List<String>> sheetAndTable = new HashMap<String, List<String>>();
        //构造第一个sheet，此sheet内有两个table
        List<String> sheet1tableNames = new ArrayList();
        sheet1tableNames.add("表一");
        sheet1tableNames.add("表二");
        //构造第二个sheet，此sheet内有一个table
        List<String> Sheet2tableNames = new ArrayList<String>();
        Sheet2tableNames.add("表三");
        sheetAndTable.put("sheet1",sheet1tableNames);
        sheetAndTable.put("sheet2",Sheet2tableNames);

        Map<String,Map<String,Class<? extends BaseRowModel>>> clazz = new HashMap<String, Map<String, Class<? extends BaseRowModel>>>();
        //第一个sheet
        Map<String,Class<? extends BaseRowModel>> tables = new HashMap<String, Class<? extends BaseRowModel>>();
        tables.put("表一",ExcelModel.class);
        tables.put("表二",ExcelModel1.class);
        clazz.put("sheet1",tables);
        Map<String,Class<? extends BaseRowModel>> tables1 = new HashMap<String, Class<? extends BaseRowModel>>();
        tables1.put("表三",ExcelModel2.class);
        clazz.put("sheet2",tables1);

        Map<String,Map<String,List<? extends BaseRowModel>>> data= new HashMap<String, Map<String, List<? extends BaseRowModel>>>();
        //第一个sheet
        Map<String,List<? extends BaseRowModel>> map1 = new HashMap<String, List<? extends BaseRowModel>>();
        map1.put("表一",excelModelList);
        map1.put("表二",excelModel1List);
        data.put("sheet1",map1);
        Map<String,List<? extends BaseRowModel>> map2 = new HashMap<String, List<? extends BaseRowModel>>();
        map2.put("表三",excelModel2List);
        data.put("sheet2",map2);

        File file1 = new File("E:\\3.xlsx");
        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        byte[] bytes = ExcelUtil.writeExcel(outputStream1,sheetAndTable,data,clazz,ExcelTypeEnum.XLSX);
        FileOutputStream outputStream = new FileOutputStream(file1);
        outputStream.write(bytes);
    }

    @Test
    void convertExcelToCsvTest() throws Exception {
        //读取excel
        File file = new File("/Users/ao/Desktop/activitycode_20200414102350.xlsx");
        InputStream inputStream = new FileInputStream(file);
        byte[] bytes = DataConvertUtil.inputStreamTobyte2(inputStream);
        ExcelConvertCsvUtil.convertExcelToCsv(bytes);
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

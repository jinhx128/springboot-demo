package com.luoyu.easyexcel.util;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.luoyu.easyexcel.listener.ModelExcelListener;
import com.luoyu.easyexcel.listener.StringExcelListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description: excel导入导出工具类
 * 1.支持按行导入字符串方式
 * 2.支持导入实体类映射
 * 3.支持按行导出字符串方式
 * 4.支持导出实体类映射
 * @Author: jinhaoxun
 * @Date: 2020/1/15 11:20
 * @Version: 1.0.0
 */
public class ExcelUtil {

    /**
     * @Author: jinhaoxun
     * @Description: 使用 StringList 来读取Excel
     * @param inputStream Excel的输入流
     * @param excelTypeEnum Excel的格式(XLS或XLSX)
     * @Date: 2020/1/16 21:40
     * @Return: java.util.List<java.util.List<java.lang.String>>
     * @Throws: Exception
     */
    public static List<List<String>> readExcel(InputStream inputStream, ExcelTypeEnum excelTypeEnum) throws Exception{
        StringExcelListener listener = new StringExcelListener();
        ExcelReader excelReader = new ExcelReader(inputStream, excelTypeEnum, null, listener);
        excelReader.read();
        return listener.getDatas();
    }

    /**
     * @Author: jinhaoxun
     * @Description: 使用模型来读取Excel
     * @param inputStream Excel的输入流
     * @param clazz 模型的类
     * @param excelTypeEnum Excel的格式(XLS或XLSX)
     * @Date: 2020/1/16 21:41
     * @Return: java.util.List<E>
     * @Throws: Exception
     */
    public static <E> List<E> readExcel(InputStream inputStream, Class<? extends BaseRowModel> clazz, ExcelTypeEnum excelTypeEnum) throws Exception {
        // 解析每行结果在listener中处理
        ModelExcelListener<E> listener = new ModelExcelListener<E>();
        ExcelReader excelReader = new ExcelReader(inputStream, excelTypeEnum, null, listener);
        //默认只有一列表头
        excelReader.read(new Sheet(1, 1, clazz));
        return listener.getDataList();
    }

    /**
     * @Author: jinhaoxun
     * @Description: 使用StringList来写入Excel，单sheet，单table
     * @param outputStream Excel的输出流
     * @param data 要写入的以StringList为单位的数据
     * @param table 配置Excel的表的属性
     * @param excelTypeEnum Excel的格式(XLS或XLSX)
     * @Date: 2020/1/16 21:42
     * @Return: void
     * @Throws: Exception
     */
    public static void writeExcel(OutputStream outputStream, List<List<String>> data, Table table, ExcelTypeEnum excelTypeEnum) throws Exception {
        /**
         * @Author: jinhaoxun
         * @Description:
         * @param outputStream
         * @param data
         * @param table
         * @param excelTypeEnum
         * @Date: 2020/1/16 21:42
         * @Return: void
         * @Throws:
         */
        //这里指定不需要表头，因为String通常表头已被包含在data里
        ExcelWriter writer = new ExcelWriter(outputStream, excelTypeEnum,false);
        //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系,无表头
        Sheet sheet1 = new Sheet(0, 0);
        writer.write0(data, sheet1,table);
        writer.finish();
    }

    /**
     * @Author: jinhaoxun
     * @Description: 使用StringList来写入Excel，单sheet，单table（返回byte数组）
     * @param outputStream Excel的输出流
     * @param data 要写入的以StringList为单位的数据
     * @param table 配置Excel的表的属性
     * @param excelTypeEnum Excel的格式(XLS或XLSX)
     * @Date: 2020/1/16 21:43
     * @Return: byte[]
     * @Throws: Exception
     */
    public static byte[] writeExcel(ByteArrayOutputStream outputStream, List<List<String>> data, Table table, ExcelTypeEnum excelTypeEnum) throws Exception {
        //这里指定不需要表头，因为String通常表头已被包含在data里
        ExcelWriter writer = new ExcelWriter(outputStream, excelTypeEnum,false);
        //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系,无表头
        Sheet sheet1 = new Sheet(0, 0);
        writer.write0(data, sheet1,table);
        writer.finish();
        return outputStream.toByteArray();
    }

    /**
     * @Author: jinhaoxun
     * @Description: 使用模型来写入Excel，单sheet，单table
     * @param outputStream Excel的输出流
     * @param data 要写入的以 模型 为单位的数据
     * @param clazz 模型的类
     * @param excelTypeEnum Excel的格式(XLS或XLSX)
     * @Date: 2020/1/16 21:43
     * @Return: byte[]
     * @Throws: Exception
     */
    public static void writeExcel(OutputStream outputStream, List<? extends BaseRowModel> data,
                                  Class<? extends BaseRowModel> clazz, ExcelTypeEnum excelTypeEnum) throws Exception {
        //这里指定需要表头，因为model通常包含表头信息
        ExcelWriter writer = new ExcelWriter(outputStream, excelTypeEnum,true);
        //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
        Sheet sheet1 = new Sheet(1, 0, clazz);
        writer.write(data, sheet1);
        writer.finish();
    }

    /**
     * @Author: jinhaoxun
     * @Description: 使用模型来写入Excel，单sheet，单table（返回字节数组）
     * @param outputStream Excel的输出流
     * @param data 要写入的以 模型 为单位的数据
     * @param clazz 模型的类
     * @param excelTypeEnum Excel的格式(XLS或XLSX)
     * @Date: 2020/1/16 21:43
     * @Return: byte[]
     * @Throws: Exception
     */
    public static byte[] writeExcel(ByteArrayOutputStream outputStream, List<? extends BaseRowModel> data,
                                    Class<? extends BaseRowModel> clazz, ExcelTypeEnum excelTypeEnum) throws Exception {
        //这里指定需要表头，因为model通常包含表头信息
        ExcelWriter writer = new ExcelWriter(outputStream, excelTypeEnum,true);
        //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
        Sheet sheet1 = new Sheet(1, 0, clazz);
        writer.write(data, sheet1);
        writer.finish();
        return outputStream.toByteArray();
    }

    /**
     * @Author: jinhaoxun
     * @Description: 使用模型来写入Excel，多sheet，单table （返回字节数组）
     * @param outputStream Excel的输出流
     * @param sheetName  sheet名集合
     * @param datas  要写入的以 模型 为单位的数据
     * @param clazzs  模型的类
     * @param excelTypeEnum  Excel的格式(XLS或XLSX)
     * @Date: 2020/1/16 21:43
     * @Return: byte[]
     * @Throws: Exception
     */
    public static byte[] writeExcel(ByteArrayOutputStream outputStream,List<String> sheetName,List<List<? extends BaseRowModel>> datas,
                                    List<Class<? extends BaseRowModel>> clazzs, ExcelTypeEnum excelTypeEnum) throws Exception {
        //这里指定需要表头，因为model通常包含表头信息
        ExcelWriter writer = new ExcelWriter(outputStream, excelTypeEnum,true);
        if (sheetName.size()!=datas.size()||datas.size()!=clazzs.size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        int i = 0;
        //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
        for (String name:sheetName){
            Sheet sheet1 = new Sheet(1, 0, clazzs.get(i));
            sheet1.setSheetName(name);
            writer.write(datas.get(i), sheet1);
        }
        writer.finish();
        return outputStream.toByteArray();
    }

    /**
     * @Author: jinhaoxun
     * @Description: 使用模型来写入Excel，多sheet，多table
     * @param outputStream  Excel的输出流
     * @param sheetAndTable sheet和table名，格式：<sheet名，<table名集合>>
     * @param data   <sheet名，<table名，table数据集>>
     * @param clazz  <sheet名，<table名，table数据集实体class类型>>
     * @param excelTypeEnum Excel的格式(XLS或XLSX)
     * @Date: 2020/1/16 21:43
     * @Return: byte[]
     * @Throws: Exception
     */
    public static byte[] writeExcel(ByteArrayOutputStream outputStream,Map<String,List<String>> sheetAndTable,
                                    Map<String,Map<String,List<? extends BaseRowModel>>> data,Map<String,Map<String,Class<? extends BaseRowModel>>> clazz,
                                    ExcelTypeEnum excelTypeEnum) throws Exception {

        //这里指定需要表头，因为model通常包含表头信息
        ExcelWriter writer = new ExcelWriter(outputStream, excelTypeEnum,true);

        Iterator<Map.Entry<String, List<String>>> iterator = sheetAndTable.entrySet().iterator();
        int sheetNo = 1;
        //遍历sheet
        while (iterator.hasNext()){
            Map.Entry<String, List<String>> next = iterator.next();
            //当前sheet名
            String sheetName = next.getKey();
            //当前sheet对应的table的实体类class对象集合
            Map<String, Class<? extends BaseRowModel>> tableClasses = clazz.get(sheetName);
            //当前sheet对应的table的数据集合
            Map<String, List<? extends BaseRowModel>> dataListMaps = data.get(sheetName);
            Sheet sheet = new Sheet(sheetNo, 0);
            sheet.setSheetName(sheetName);
            int tableNo = 1;
            Iterator<Map.Entry<String, Class<? extends BaseRowModel>>> iterator1 = tableClasses.entrySet().iterator();
            //遍历table
            while (iterator1.hasNext()){
                Map.Entry<String, Class<? extends BaseRowModel>> next1 = iterator1.next();
                //当前table名
                String tableName = next1.getKey();
                //当前table对应的class
                Class<? extends BaseRowModel> tableClass = next1.getValue();
                //当前table对应的数据集
                List<? extends BaseRowModel> tableData = dataListMaps.get(tableName);
                Table table = new Table(tableNo);
                table.setClazz(tableClass);
                writer.write(tableData, sheet, table);
                tableNo++;
            }
            sheetNo++;
        }
        writer.finish();
        return outputStream.toByteArray();
    }
}

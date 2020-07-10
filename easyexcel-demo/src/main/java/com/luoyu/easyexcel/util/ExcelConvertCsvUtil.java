package com.luoyu.easyexcel.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: excel文件转成csv格式
 * @Author: jinhaoxun
 * @Date: 2020/4/14 上午10:07
 * @Version: 1.0.0
 */
public class ExcelConvertCsvUtil {

    /**
     * @Author: jinhaoxun
     * @Description: 将excel字节码数组转成csv字节码数组
     * @param bytes excel字节码数组
     * @Date: 2020/1/16 21:43
     * @Return: byte[]
     * @Throws: Exception
     */
    public static byte[] convertExcelToCsv(byte[] bytes) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        Workbook wb = WorkbookFactory.create(inputStream);

        String buffer = "";
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;

        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 0; i<rownum; i++) {
                row = sheet.getRow(i);
                for (int j = 0; j < colnum; j++) {
                    cellData = (String) getCellFormatValue(row.getCell(j));
                    buffer +=cellData;
                }
                buffer = buffer.substring(0, buffer.lastIndexOf(",")).toString();
                buffer += "\n";
            }

            return buffer.getBytes();
        }
        return null;
    }

    /**
     * @Author: jinhaoxun
     * @Description: 读取excel
     * @param filePath 本地文件路径
     * @Date: 2020/1/16 21:43
     * @Return: Workbook
     * @Throws: Exception
     */
    public static Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param cell
     * @Date: 2020/1/16 21:43
     * @Return: Workbook
     * @Throws: Exception
     */
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
                case NUMERIC:{
                    cellValue = String.valueOf(cell.getNumericCellValue()).replaceAll("\n", " ") + ",";
                    break;
                }
                case FORMULA:{
                    //判断cell是否为日期格式
                    if(DateUtil.isCellDateFormatted(cell)){
                        //转换为日期格式YYYY-mm-dd
                        cellValue = String.valueOf(cell.getDateCellValue()).replaceAll("\n", " ") + ",";;
                    }else{
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue()).replaceAll("\n", " ") + ",";;
                    }
                    break;
                }
                case STRING:{
                    cellValue = cell.getRichStringCellValue().getString().replaceAll("\n", " ") + ",";;
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }
}

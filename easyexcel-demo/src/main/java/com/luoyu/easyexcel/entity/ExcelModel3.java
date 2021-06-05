package com.luoyu.easyexcel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @Description: 生成excel文件数据字段模板
 * @Author: jinhaoxun
 * @Date: 2020/1/14 15:45
 * @Version: 1.0.0
 */
@Data
public class ExcelModel3 extends BaseRowModel {

    public ExcelModel3(){
    }

    public ExcelModel3(String t1, String t2, String t3, String t4, String t5){
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
    }

    @ExcelProperty(value = "t1", index = 0)
    private String t1;
    @ExcelProperty(value = "t2", index = 1)
    private String t2;
    @ExcelProperty(value = "t3", index = 2)
    private String t3;
    @ExcelProperty(value = "t4", index = 3)
    private String t4;
    @ExcelProperty(value = "t5", index = 4)
    private String t5;

}

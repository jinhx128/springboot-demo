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
public class ExcelModel extends BaseRowModel {

    public ExcelModel(){
    }

    public ExcelModel(String dateJuly, String onDuty, String offDuty, String overtime, String last){
        this.dateJuly = dateJuly;
        this.onDuty = onDuty;
        this.offDuty = offDuty;
        this.overtime = overtime;
        this.last = last;
    }

    @ExcelProperty(value = "日期", index = 0)
    private String dateJuly;
    @ExcelProperty(value = "上班时间", index = 1)
    private String onDuty;
    @ExcelProperty(value = "下班时间", index = 2)
    private String offDuty;
    @ExcelProperty(value = "加班时长", index = 3)
    private String overtime;
    @ExcelProperty(value = "备注", index = 4)
    private String last;

}

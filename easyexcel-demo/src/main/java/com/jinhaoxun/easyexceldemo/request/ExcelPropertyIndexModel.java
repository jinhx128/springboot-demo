package com.jinhaoxun.easyexceldemo.request;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/1/14 15:45
 * @Version: 1.0
 */
@Getter
@Setter
public class ExcelPropertyIndexModel extends BaseRowModel {

    @ExcelProperty(value = "日期", index = 0)
    private String dateJuly;
    @ExcelProperty(value = "上班时间", index = 1)
    private String onDuty;
    @ExcelProperty(value = "下班时间", index = 2)
    private String offDuty;
    @ExcelProperty(value = "加班时长", index = 3)
    private String overtime;
    @ExcelProperty(value = "备注", index = 6)
    private String last;

}

package com.jinhaoxun.easyexceldemo;

import com.alibaba.excel.metadata.Sheet;
import com.jinhaoxun.easyexceldemo.request.ExcelPropertyIndexModel;
import com.jinhaoxun.easyexceldemo.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class EasyexcelDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void writerExcel() throws Exception {
        List<List<Object>> lists = ExcelUtils.readExcel("\\d\\file\\测试.xlsx");
        if (lists != null) {
            log.info("表数据："+lists);
        } else {
            log.info("空异常！");
        }
        log.info(lists.toString());
    }

    @Test
    void exportExcel() throws Exception {
        List<List<Object>> lists = new ArrayList<>();
        for(int i = 0; i < 50 ; i++){
            List<Object> list = new ArrayList<>();
            list.add("日期" + i);
            list.add("上班时间" + i);
            list.add("下班时间" + i);
            list.add("加班时长" + i);
            list.add("备注" + i);
            lists.add(list);
        }
        Sheet sheet1 = new Sheet(1, 0, ExcelPropertyIndexModel.class);
        ExcelUtils.writeSimpleBySheet("D:\\file\\测试.xlsx",lists,sheet1);
    }

}

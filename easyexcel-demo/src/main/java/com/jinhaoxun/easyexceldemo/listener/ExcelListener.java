package com.jinhaoxun.easyexceldemo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/1/14 15:51
 * @Version: 1.0
 */
public class ExcelListener extends AnalysisEventListener {

    public List<List<Object>> datas = new ArrayList<>();

    public List<List<Object>> getDatas() {
        return datas;
    }

    public void setDatas(List<List<Object>> datas) {
        this.datas = datas;
    }

    @Override
    public void invoke(Object object, AnalysisContext context) {
        List<Object> stringList = (List<Object>) object;
        datas.add(stringList);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //解析结束销毁不用的资源
        datas.clear();
    }
}

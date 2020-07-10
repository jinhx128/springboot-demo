package com.luoyu.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description: 模型解析监听器 -- 每解析一行会回调invoke()方法，整个excel解析结束会执行doAfterAllAnalysed()方法
 * @Author: jinhaoxun
 * @Date: 2020/1/14 15:51
 * @Version: 1.0.0
 */
public class ModelExcelListener<E> extends AnalysisEventListener<E> {

    private List<E> dataList = new ArrayList<E>();

    @Override
    public void invoke(E object, AnalysisContext context) {
        dataList.add(object);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }

    public List<E> getDataList() {
        return dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }
}

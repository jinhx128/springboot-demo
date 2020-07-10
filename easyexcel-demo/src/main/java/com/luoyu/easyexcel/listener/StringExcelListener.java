package com.luoyu.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: StringList 解析监听器
 * @Author: jinhaoxun
 * @Date: 2020/1/15 11:31
 * @Version: 1.0.0
 */
public class StringExcelListener extends AnalysisEventListener {
    /**
     * 自定义用于暂时存储data
     * 可以通过实例获取该值
     */
    private List<List<String>> datas = new ArrayList<List<String>>();

    /**
     * 每解析一行都会回调invoke()方法
     *
     * @param object
     * @param context
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        List<String> stringList= (List<String>) object;
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        datas.add(stringList);
        //根据自己业务做处理
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //解析结束销毁不用的资源
        //注意不要调用datas.clear(),否则getDatas为null
    }

    public List<List<String>> getDatas() {
        return datas;
    }

    public void setDatas(List<List<String>> datas) {
        this.datas = datas;
    }
}

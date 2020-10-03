package com.luoyu.aop.vo.request;

import com.luoyu.aop.vo.base.Model;
import lombok.Data;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/7/10 10:36 上午
 * @Version: 1.0.0
 */
@Data
public class TestRequest extends Model {

    private int id;
    private String name;
    private String sex;

}

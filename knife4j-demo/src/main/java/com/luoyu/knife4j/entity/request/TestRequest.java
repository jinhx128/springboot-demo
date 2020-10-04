package com.luoyu.knife4j.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: luoyu
 * @Date: 2020/5/14 9:30 下午
 * @Version: 1.0.0
 */
@Data
public class TestRequest {

    @ApiModelProperty(required = true, value = "用户id", example = "123")
    private String id;

    @ApiModelProperty(required = true, value = "用户name", example = "百里玄刺")
    private String name;

    @ApiModelProperty(required = true, value = "用户age", example = "18")
    private int age;

    @ApiModelProperty(required = true, value = "用户sex", example = "男")
    private String sex;

}

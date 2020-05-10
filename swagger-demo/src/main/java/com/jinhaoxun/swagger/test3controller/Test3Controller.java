package com.jinhaoxun.swagger.test3controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jinhaoxun
 * @since 2020-02-13
 */
@RestController
@RequestMapping("/test3")
@Api("test3接口")
public class Test3Controller {

    /**
     * @author jinhaoxun
     * @description 测试接口3
     */
    @GetMapping(value = "/get", produces = "application/json; charset=UTF-8")
    @ApiOperation("test3接口")
    public String getArticleList() throws Exception {
        return "返回成功！";
    }

}

package com.jinhaoxun.swagger.test1controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/test1")
@Api("test1接口")
public class Test1Controller {

    /**
     * @author jinhaoxun
     * @description 测试接口1
     */
    @PostMapping(value = "/get", produces = "application/json; charset=UTF-8")
    @ApiOperation("test1接口")
    public String getArticleList(String string) throws Exception {
        return string + "：返回成功！";
    }

}

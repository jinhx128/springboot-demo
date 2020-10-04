package com.luoyu.knife4j.controller.test1;

import com.luoyu.knife4j.entity.request.TestRequest;
import com.luoyu.knife4j.entity.response.TestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author luoyu
 * @since 2020-02-13
 */
@RestController
@RequestMapping("/test1")
@Api(value = "/test1", tags = "Test1Controller接口")
public class Test1Controller {

    /**
     * @author luoyu
     * @description 测试接口1
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功！"),
            @ApiResponse(code = 500, message = "服务器内部错误，请联系管理人员！"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/gettest1", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "test1接口名称", notes = "test1接口描述")
    public TestResponse getTest1(TestRequest request) throws Exception {
        TestResponse response = new TestResponse();
        BeanUtils.copyProperties(request, response);
        return response;
    }

    /**
     * @author luoyu
     * @description 测试接口2
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功！"),
            @ApiResponse(code = 500, message = "服务器内部错误，请联系管理人员！"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/gettest2", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "test2接口名称", notes = "test2接口描述")
    public TestResponse getTest2(TestRequest request) throws Exception {
        TestResponse response = new TestResponse();
        BeanUtils.copyProperties(request, response);
        return response;
    }

}

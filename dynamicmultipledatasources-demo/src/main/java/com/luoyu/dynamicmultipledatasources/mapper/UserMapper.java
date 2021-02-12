package com.luoyu.dynamicmultipledatasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luoyu.dynamicmultipledatasources.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jinhaoxun
 * @since 2020-02-13
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param id id
     * @Date: 2020/2/13 下午12:06
     * @Throws:
     */
    User selectById(Integer id);

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param id id
     * @param name 姓名
     * @Date: 2020/2/13 下午12:06
     * @Return: int
     * @Throws:
     */
    int updateName(@Param("id") int id, @Param("name") String name);

}

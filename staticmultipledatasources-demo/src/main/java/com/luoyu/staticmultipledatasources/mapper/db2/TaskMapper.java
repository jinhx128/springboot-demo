package com.luoyu.staticmultipledatasources.mapper.db2;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luoyu.staticmultipledatasources.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jinhaoxun
 * @since 2020-02-13
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param id id
     * @Date: 2020/2/13 下午12:06
     * @Throws:
     */
    Task selectById(Integer id);

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

package com.jinhx.shardingjdbc.entity;

import com.jinhx.shardingjdbc.util.SnowFlakeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * User
 *
 * @author jinhx
 * @date 2021-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分表的数量，一定要2的n次方
     */
    public static final int TABLE_COUNT = 4;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Long age;

    /**
     * 邮箱
     */
    private String email;

    public void setId(){
        if (Objects.isNull(this.age)){
            throw new RuntimeException("age为空，无法生成id");
        }
        this.id = SnowFlakeUtil.getSnowflakeId(this.age & (TABLE_COUNT - 1));
    }

}

package com.jinhx.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jinhx.shardingjdbc.util.SnowFlakeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * Order
 *
 * @author jinhx
 * @since 2021-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("my_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分表的数量，一定要2的n次方
     */
    public static final int TABLE_COUNT = 4;

    /**
     * 订单id主键
     */
    @TableId(type = IdType.INPUT)
    private Long orderId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 金额
     */
    private Long money;

    public void buildOrderId(){
        if (Objects.isNull(this.userId)){
            throw new RuntimeException("userId为空，无法生成orderId");
        }
        this.orderId = SnowFlakeUtil.getSnowflakeId(SnowFlakeUtil.getDataCenterId(this.userId) & (TABLE_COUNT - 1));
    }

    public void buildUserId(Integer dataCenterId){
        if (Objects.isNull(dataCenterId)){
            throw new RuntimeException("dataCenterId为空，无法生成userId");
        }
        this.userId = SnowFlakeUtil.getSnowflakeId(dataCenterId & (TABLE_COUNT - 1));
    }

}

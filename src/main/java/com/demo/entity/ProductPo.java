package com.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 商品实体
 */
@Data
@TableName("product")
public class ProductPo {

    @TableId
    private Long id;
    private String name;
    private Long categoryId;
}

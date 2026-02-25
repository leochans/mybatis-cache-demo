package com.demo.service;

import com.demo.entity.ProductPo;
import com.demo.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 模拟创建商品快照副本：内部 selectById 获取对象后，直接修改该对象并 insert 新记录。
 * 与 placeOrder 在同一个事务中时，selectById 会返回 MyBatis 缓存的同一实例，
 * 对它的修改会影响到 placeOrder 中持有的 productPo。
 */
@Service
public class ProductCopyService {

    private static final AtomicLong ID_GEN = new AtomicLong(10000);

    @Autowired
    private ProductMapper productMapper;

    @Transactional(rollbackFor = Exception.class)
    public void createSnapshot(Long productId) {
        // 第二次查询相同 id：MyBatis 一级缓存返回【同一个对象实例】
        ProductPo productPo = productMapper.selectById(productId);
        if (productPo == null) {
            throw new IllegalStateException("商品不存在");
        }
        long newId = ID_GEN.incrementAndGet();
        // 直接修改查询出来的对象！（问题所在）
        productPo.setId(newId);           // 改成新 id
        productPo.setCategoryId(888L);    // 改成新类目 id
        productPo.setName("快照副本");
        productMapper.insert(productPo);
        // 注意：此时 placeOrder 里持有的「同一个对象」也已被修改
    }
}

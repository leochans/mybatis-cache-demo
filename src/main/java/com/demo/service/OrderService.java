package com.demo.service;

import com.demo.entity.ProductPo;
import com.demo.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 模拟下单流程：先 selectById 得到 productPo，再调用 createSnapshot。
 * 由于在同一事务中，createSnapshot 内部再次 selectById 时，
 * MyBatis 返回的是【同一个对象实例】，修改会直接影响本方法持有的 productPo。
 */
@Service
public class OrderService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductCopyService productCopyService;

    @Transactional(rollbackFor = Exception.class)
    public DemoResult placeOrder(Long productId) {
        // 第一次查询：得到对象 A
        ProductPo productPo = productMapper.selectById(productId);
        if (productPo == null) {
            throw new IllegalStateException("商品不存在");
        }
        Long idBeforeCall = productPo.getId();
        Long categoryIdBeforeCall = productPo.getCategoryId();
        String nameBeforeCall = productPo.getName();

        // 调用 createSnapshot：内部会再次 selectById，拿到【同一实例】并修改
        productCopyService.createSnapshot(productId);

        // 调用返回后，productPo 引用没变，但对象内容已被 createSnapshot 改过了！
        Long idAfterCall = productPo.getId();
        Long categoryIdAfterCall = productPo.getCategoryId();
        String nameAfterCall = productPo.getName();

        return DemoResult.builder()
                .idBeforeCall(idBeforeCall)
                .categoryIdBeforeCall(categoryIdBeforeCall)
                .nameBeforeCall(nameBeforeCall)
                .idAfterCall(idAfterCall)
                .categoryIdAfterCall(categoryIdAfterCall)
                .nameAfterCall(nameAfterCall)
                .dataCorrupted(!idBeforeCall.equals(idAfterCall))
                .build();
    }
}

package com.demo.controller;

import com.demo.service.DemoResult;
import com.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 演示入口：
 * 访问 http://localhost:8081/demo/place-order?productId=1
 * 观察返回结果中「调用前后」productPo 的字段变化，证明对象被意外修改
 */
@RestController
public class DemoController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/demo/place-order")
    public DemoResult demoPlaceOrder(@RequestParam(defaultValue = "1") Long productId) {
        return orderService.placeOrder(productId);
    }
}

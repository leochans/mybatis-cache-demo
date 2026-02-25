package com.demo.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DemoResult {
    private Long idBeforeCall;
    private Long categoryIdBeforeCall;
    private String nameBeforeCall;
    private Long idAfterCall;
    private Long categoryIdAfterCall;
    private String nameAfterCall;
    /** 调用 createSnapshot 后，原始对象是否被意外修改（true=出现问题） */
    private Boolean dataCorrupted;
}

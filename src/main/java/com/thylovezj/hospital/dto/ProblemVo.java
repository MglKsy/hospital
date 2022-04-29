package com.thylovezj.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 问题模块返回给前端的问题
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemVo {
    /**
     * 问题名
     */
    private String name;

    /**
     * 问题答案
     */
    private Integer answer;

    /**
     * 问题种类
     */
    private Integer type;

    /**
     * 医生Id-出题人Id
     */
    private Integer doctorId;

    /**
     * 问题选项
     */
    private String checks;

    /**
     * 问题分值
     */
    private int score;
}

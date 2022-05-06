package com.thylovezj.hospital.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value ="xdu_hospital_problem")
@Data
public class ProblemVo implements Serializable {

    /**
     * 问题题干
     */
    private String name;

    /**
     * 问题选项
     */
    private String checks;

    /**
     * 问题分值
     */
    private double score;

    /**
     * 问题类型
     */
    private Integer type;

    /**
     * 问题答案 1-a 2-b 3-c
     */
    public Integer answer;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

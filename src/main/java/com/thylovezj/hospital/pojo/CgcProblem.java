package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 问题
 * @TableName xdu_hospital_problem_cgc
 */
@TableName(value ="xdu_hospital_problem_cgc")
@Data
public class CgcProblem implements Serializable {
    /**
     *问题主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    public Integer id;

    /**
     *问题题干
     */
    public String name;

    /**
     * 问题答案 1-a 2-b 3-c
     */
    public Integer answer;

    /**
     *问题种类 1-客观题 2-主观题
     */
    public Integer type;

    /**
     *出题人医生Id，关联出题人
     */
    public Integer doctorId;

    /**
     *题目创建时间
     */
    public Date createTime;

    /**
     * 题目更新时间
     */
    public Date updateTime;

    /**
     * 问题选项 格式[a.xxx,b.xxx,c.xxx]中间用逗号分割
     */
    public String checks;

    /**
     * 问题分值
     */
    public Double score;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

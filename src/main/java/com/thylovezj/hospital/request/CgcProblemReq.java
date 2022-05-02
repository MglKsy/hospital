package com.thylovezj.hospital.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 问题
 * @TableName xdu_hospital_problem
 */
@TableName(value ="xdu_hospital_problem_cgc")
@Data
public class CgcProblemReq implements Serializable {
    /**
     *问题题干
     */
    public String name;

    /**
     * 问题答案 1-a 2-b 3-c
     */
    public Integer answer;

    /**
     *问题种类 0-客观题 1-主观题 2-图片题
     */
    public Integer type;

    /**
     *出题人医生Id，关联出题人
     */
    public Integer doctorId;

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

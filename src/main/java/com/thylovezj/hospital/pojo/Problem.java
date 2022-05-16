package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌曲表
 */
@TableName(value ="xdu_hospital_problem")
@Data
public class Problem implements Serializable {
    /**
     * 普通问题主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

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
     * 医生Id
     */
    private String doctorId;

    /**
     * 问题创建时间
     */
    private Date createTime;

    /**
     * 题目更新时间
     */
    public Date updateTime;

    /**
     * 问题答案 1-a 2-b 3-c
     */
    public Integer answer;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 长谷川回答表
 */

@TableName(value = "xdu_hospital_answer_cgc")
@Data
public class CgcAnswer implements Serializable {
    /**
     * 问题主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    public Integer id;

    /**
     * 用户唯一Id
     */
    public String openId;

    /**
     * 子女唯一Id
     */
    public String sonId;

    /**
     * 题目Id
     */
    public Integer questionId;

    /**
     * 用户作答
     */
    public String ans;

    /**
     * 作答时间
     */
    public Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 结果表 xdu_hospital_result_cgc
 */
@TableName(value = "xdu_hospital_result_cgc")
@Data
public class CgcResult implements Serializable {
    /**
     * 结果表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 病人id
     */
    private String pid;

    /**
     * 病人子女Id
     */
    private String sonId;

    /**
     * 客观题分数
     */
    private double subScore;

    /**
     * 主观题分数
     */
    private double objScore;

    /**
     * 结果状态 1-正在批改主主观题 2-试题已全部批改结束
     */
    private Integer state;

    /**
     * 作答时间
     */
    private Date createTime;

    /**
     * 批改结束时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

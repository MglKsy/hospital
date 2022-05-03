package com.thylovezj.hospital.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@TableName(value = "xdu_hospital_exercise")
@Data
public class ExerciseReq implements Serializable {
    /**
     * 病人Id
     */
    private String pid;

    /**
     * 子女Id
     */
    private String sonId;

    /**
     * 训练时长
     */
    private String exTime;

    /**
     * 训练类型
     */
    private Integer exType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
package com.thylovezj.hospital.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value ="xdu_hospital_exercise")
@Data
public class ExerciseVo implements Serializable {

    /**
     * 训练时长
     */
    private String exTime;

    /**
     * 训练类型
     */
    private Integer exType;

    /**
     * 训练得分
     */
    private Integer score;

    /**
     * 训练日期(结束)
     */
    private Date exDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

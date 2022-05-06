package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value ="xdu_hospital_exercise")
@Data
public class Exercise implements Serializable {
    /**
     * 锻炼表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    /**
     * 训练得分
     */
    private Integer score;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName xdu_hospital_gesture
 */
@TableName(value ="xdu_hospital_gesture")
@Data
public class Gesture implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String recognizeName;

    /**
     * 
     */
    private String resultName;

    /**
     * 
     */
    private String image;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
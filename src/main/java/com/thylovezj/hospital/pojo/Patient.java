package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName xdu_hospital_patient
 */
@TableName(value ="xdu_hospital_patient")
@Data
public class Patient implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * open_id，和user表对应
     */
    private String openId;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 住址
     */
    private String address;

    /**
     * 出生省份
     */
    private String province;

    /**
     * 文化程度

     */
    private String eduBackground;

    /**
     * 子女照片
     */
    private String childPhotos;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
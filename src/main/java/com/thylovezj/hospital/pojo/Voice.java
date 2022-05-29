package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName xdu_hospital_voice
 */
@TableName(value ="xdu_hospital_voice")
@Data
public class Voice implements Serializable {
    /**
     * 声音表主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 子女的主键
     */
    private String openId;

    /**
     * 子女声音的url
     */
    private String url;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 微信用户信息
 * @TableName xdu_hospital_user
 */
@TableName(value ="xdu_hospital_user")
@Data
public class User implements Serializable {
    /**
     * open_id
     */
    @TableId
    private String openId;

    /**
     * 用户角色1.患者2.医生3.管理员
     */
    private Integer role;

    private LocalDateTime createTime;



}
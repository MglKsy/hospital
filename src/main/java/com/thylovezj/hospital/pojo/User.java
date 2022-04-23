package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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


    /**
     * 性别 女:0  男:1
     */
    private Integer sex;

    /**
     * 个性签名
     */
    private String personalizedSignature;

    /**
     * 网名
     */
    private String nickName;

    /**
     * 年龄
     */
    private Integer age;
    /**
     * 积分
     */
    private Integer bonus;

    /**
     * 电话号码
     */

    private String phone;
    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 用户更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
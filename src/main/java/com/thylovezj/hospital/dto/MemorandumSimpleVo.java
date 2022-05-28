package com.thylovezj.hospital.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 *
 * @author thylovezj
 */
@TableName(value = "xdu_hospital_memorandum")
@Data
public class MemorandumSimpleVo {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    private String title;

    private Date updateTime;

}

package com.thylovezj.hospital.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value ="xdu_hospital_answer_cgc")
public class CgcAnswerReq implements Serializable {
    public String openId;

    public Integer questionId;

    public Integer subAns;

    public String objAns;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

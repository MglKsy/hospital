package com.thylovezj.hospital.request;

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
public class MemorandumReq {
    private String title;

    private String content;
}

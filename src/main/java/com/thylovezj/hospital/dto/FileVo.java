package com.thylovezj.hospital.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value ="xdu_hospital_file")
@Data
public class FileVo {

    private String oldName;

    private double fileSize;

    private String filePath;

    private String fileType;

    private String userId;

    private Date updateTime;

    private long dirId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

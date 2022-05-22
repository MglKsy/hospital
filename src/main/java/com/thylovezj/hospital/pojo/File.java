package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value ="xdu_hospital_file")
@Data
public class File {
    @TableId(type = IdType.AUTO)
    private long fileId;

    private String oldName;

    private double fileSize;

    private String filePath;

    private String fileType;

    private String userId;

    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

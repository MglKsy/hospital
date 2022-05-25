package com.thylovezj.hospital.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thylovezj.hospital.pojo.Folder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TableName(value ="xdu_hospital_file")
@Data
public class FileVo {

    private String oldName;

    private String fileSize;

    private String filePath;

    private String fileType;

    private Date updateTime;

    private String dirId;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

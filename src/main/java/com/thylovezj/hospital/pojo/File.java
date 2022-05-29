package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author thylovezj
 */

@TableName(value ="xdu_hospital_file")
@Data
public class File {
    @TableId(type = IdType.AUTO)
    private long fileId;

    private String oldName;

    private String fileSize;

    private String filePath;

    private String fileType;

    private String userId;

    private Date updateTime;

    private String dirId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

package com.thylovezj.hospital.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "xdu_hospital_folder")
@Data
public class Folder {
    @TableId(type = IdType.AUTO)
    private long folderId;

    private String folderName;

    private String userId;

    private String folderRemark;

    private long parentId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}


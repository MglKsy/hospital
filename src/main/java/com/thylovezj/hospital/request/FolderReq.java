package com.thylovezj.hospital.request;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "xdu_hospital_folder")
@Data
public class FolderReq {
    /**
     * 文件夹名称
     */
    private String folderName;

    /**
     * 文件夹描述
     */
    private String folderRemark;

    /**
     * 父文件夹Id
     */
    private String parentId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}


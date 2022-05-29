package com.thylovezj.hospital.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thylovezj
 */
@TableName(value = "xdu_hospital_folder")
@Data
public class FolderVo {
    @TableId(type = IdType.AUTO)
    public String folderId;

    public String folderName;

    public String folderRemark;

    private List<FileVo> fileVos = new ArrayList<>();

    private List<FolderVo> folderVos = new ArrayList<>();

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}


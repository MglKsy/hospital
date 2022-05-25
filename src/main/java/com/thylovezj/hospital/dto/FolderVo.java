package com.thylovezj.hospital.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@TableName(value = "xdu_hospital_folder")
@Data
public class FolderVo {

    public String folderName;

    public String folderRemark;

    public String folderId;

    private List<FileVo> fileVos = new ArrayList<>();

    private List<FolderVo> folderVos = new ArrayList<>();

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}


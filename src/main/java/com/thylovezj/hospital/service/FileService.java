package com.thylovezj.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thylovezj.hospital.dto.FileVo;
import com.thylovezj.hospital.pojo.File;

import java.util.List;

public interface FileService extends IService<File> {
    List<FileVo> getFileList(long folderId);
}

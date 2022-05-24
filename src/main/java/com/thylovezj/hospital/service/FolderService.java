package com.thylovezj.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thylovezj.hospital.pojo.Folder;

import java.util.List;

public interface FolderService extends IService<Folder> {
    List<Folder> getFolders(long folderId);
}

package com.thylovezj.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thylovezj.hospital.dto.FileVo;
import com.thylovezj.hospital.dto.FolderVo;
import com.thylovezj.hospital.pojo.Folder;
import com.thylovezj.hospital.request.FolderReq;

import java.util.List;

public interface FolderService extends IService<Folder> {
    List<Folder> getFolders(String folderId);

    FolderVo addFolder(FolderReq folderReq);

    List<FileVo> listFileAndFolders(String folderId);
}

package com.thylovezj.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thylovezj.hospital.dto.FileVo;
import com.thylovezj.hospital.pojo.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService extends IService<File> {
    /**
     * 获取folderId下所有文件
     * @param folderId
     * @return
     */
    List<FileVo> getFileList(String folderId);

    /**
     * 添加FileReq并返回FileVo对象
     * @param file
     * @param parentId
     * @return
     * @throws IOException
     */
    FileVo addFile(MultipartFile file,String parentId) throws IOException;
}

package com.thylovezj.hospital.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.dto.FileVo;
import com.thylovezj.hospital.dto.FolderVo;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.mapper.FileMapper;
import com.thylovezj.hospital.mapper.FolderMapper;
import com.thylovezj.hospital.pojo.File;
import com.thylovezj.hospital.pojo.Folder;
import com.thylovezj.hospital.request.FolderReq;
import com.thylovezj.hospital.service.FolderService;
import com.thylovezj.hospital.util.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FolderServiceImpl extends ServiceImpl<FolderMapper, Folder> implements FolderService {
    @Resource
    private FolderMapper folderMapper;

    @Resource
    private FileMapper fileMapper;

    @Override
    public List<Folder> getFolders(String folderId) {
        List<Folder> folders;
        if (StrUtil.isBlank(folderId)) {
            //如果folderId为空，说明需要查询所有根目录下文件夹
            QueryWrapper<Folder> queryWrapper = new QueryWrapper<Folder>()
                    .eq("parent_id", "").eq("user_id", "sss");
            folders = folderMapper.selectList(queryWrapper);
        } else {
            QueryWrapper<Folder> folderQueryWrapper = new QueryWrapper<>();
            QueryWrapper<Folder> queryWrapper = folderQueryWrapper
                    .eq("parent_id", folderId).eq("user_id", "sss");
            folders = folderMapper.selectList(queryWrapper);
        }
        return folders;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FolderVo addFolder(FolderReq folderReq) {
        Folder folder = new Folder();
        String id = "sss";
        QueryWrapper<Folder> folderQueryWrapper = new QueryWrapper<Folder>();
        if (StrUtil.isBlank(folderReq.getParentId())) {
            folderQueryWrapper
                    .eq("user_id", "sss").eq("parent_id", "").eq("folder_name", folderReq.getFolderName());
        } else {
            folderQueryWrapper
                    .eq("user_id", "sss").eq("parent_id", Convert.toLong(folderReq.getParentId()))
                    .eq("folder_name", folderReq.getFolderName());
        }
        List<Folder> folders = folderMapper.selectList(folderQueryWrapper);
        if (folders.size() != 0) {
            folderReq.setFolderName(folderReq.getFolderName() + UUID.randomUUID());
        }
        BeanUtils.copyProperties(folderReq, folder);
        folder.setUserId(id);
        int insert = folderMapper.insert(folder);
        if (insert == 0) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.INSERT_FAILED);
        }
        long folderId = folder.getFolderId();
        FolderVo folderVo = new FolderVo();
        folderVo.setFolderName(folderReq.getFolderName());
        folderVo.setFolderRemark(folderReq.getFolderRemark());
        folderVo.setFolderId(StrUtil.toString(folderId));
        return folderVo;
    }

    @Override
    public List<FileVo> listFileAndFolders(String parentId) {
        //根目录
        FolderVo folderVo = new FolderVo();
        QueryWrapper<Folder> folderQueryWrapper = new QueryWrapper<Folder>()
                .eq("user", "sss").eq("parent_id", parentId);
        List<Folder> folders = folderMapper.selectList(folderQueryWrapper);
        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<File>()
                .eq("user", "sss").eq("dir_id", "");
        List<File> files = fileMapper.selectList(fileQueryWrapper);
        ArrayList<FileVo> fileVos = new ArrayList<>();
        ArrayList<FolderVo> folderVos = new ArrayList<>();
        files.stream().forEach((file -> {
            FileVo fileVo = new FileVo();
            BeanUtils.copyProperties(file,fileVo);
            fileVos.add(fileVo);
        }));

        folders.stream().forEach((folder -> {
            FolderVo folderVo1 = new FolderVo();
            BeanUtils.copyProperties(folder,folderVo1);
            folderVos.add(folderVo1);
        }));

        folderVo.setFileVos(fileVos);
        folderVo.setFolderVos(folderVos);

        recursivelyFindFileAndFolders(folderVos);
        return null;
    }

    private void recursivelyFindFileAndFolders(List<FolderVo> folderVos) {

    }
}

package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.mapper.FolderMapper;
import com.thylovezj.hospital.pojo.Folder;
import com.thylovezj.hospital.request.FolderReq;
import com.thylovezj.hospital.service.FolderService;
import com.thylovezj.hospital.util.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

public class FolderServiceImpl extends ServiceImpl<FolderMapper, Folder> implements FolderService {
    @Resource
    private FolderMapper folderMapper;

    @Override
    public List<Folder> getFolders(long folderId) {
        QueryWrapper<Folder> folderQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Folder> queryWrapper = folderQueryWrapper
                .eq("parent_id", folderId).eq("user_id", UserHolder.getId());
        List<Folder> folders = folderMapper.selectList(queryWrapper);
        return folders;
    }

    @Override
    @Transactional
    public void addFolder(FolderReq folderReq) {
        Folder folder = new Folder();
        String id = UserHolder.getId();
        BeanUtils.copyProperties(folderReq, folder);
        folder.setUserId(id);
        int insert = folderMapper.insert(folder);
        if (insert == 0) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.INSERT_FAILED);
        }
    }
}

package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.dto.FileVo;
import com.thylovezj.hospital.mapper.FileMapper;
import com.thylovezj.hospital.pojo.File;
import com.thylovezj.hospital.service.FileService;
import com.thylovezj.hospital.util.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    @Resource
    private FileMapper fileMapper;

    /**
     * 根据文件夹Id查找其路径下的文件
     */
    @Override
    public List<FileVo> getFileList(long folderId) {
        ArrayList<FileVo> fileVos = new ArrayList<>();
        //获取当前用户userId
        String uid = UserHolder.getId();
        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<File>()
                .eq("user_id", uid).eq("dir_id", folderId);
        /*
         * 获取了FileList
         */
        List<File> files = fileMapper.selectList(fileQueryWrapper);
        files.stream().forEach((file)->{
            FileVo fileVo = new FileVo();
            BeanUtils.copyProperties(file,fileVo);
            fileVos.add(fileVo);
        });
        return fileVos;
    }
}

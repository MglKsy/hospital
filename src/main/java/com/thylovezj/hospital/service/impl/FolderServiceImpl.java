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
import com.thylovezj.hospital.service.FileService;
import com.thylovezj.hospital.service.FolderService;
import com.thylovezj.hospital.util.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author thylovezj
 */
@Service
public class FolderServiceImpl extends ServiceImpl<FolderMapper, Folder> implements FolderService {
    @Resource
    private FolderMapper folderMapper;

    @Resource
    private FileService fileService;

    /**
     * @param folderId 获取该文件夹Id下所有文件夹
     * @return
     */
    @Override
    public List<FolderVo> getFolders(String folderId) {
        List<FolderVo> folders;
        if (StrUtil.isBlank(folderId)) {
            //如果folderId为空，说明需要查询所有根目录下文件夹
            folders = FindFordersInDatabases("", "sss");
        } else {
            //如果folderId不为空，说明需要查询所有folderId下的文件夹
            folders = FindFordersInDatabases(folderId, "sss");
        }
        return folders;
    }

    /**
     * 根据父文件夹和用户Id去数据库中查询文件夹列表
     *
     * @param parentId
     * @param userId
     * @return folderVos 符合条件的文件夹Vo
     */
    private List<FolderVo> FindFordersInDatabases(String parentId, String userId) {
        List<Folder> folders;
        QueryWrapper<Folder> queryWrapper = new QueryWrapper<Folder>()
                .eq("parent_id", parentId).eq("user_id", userId);
        folders = folderMapper.selectList(queryWrapper);
        ArrayList<FolderVo> folderVos = new ArrayList<>();

        folders.stream().forEach((folder -> {
            FolderVo folderVo1 = new FolderVo();
            BeanUtils.copyProperties(folder, folderVo1);
            folderVo1.setFolderId(StrUtil.toString(folder.getFolderId()));
            folderVos.add(folderVo1);
        }));

        return folderVos;
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

    /**
     * 获取父文件夹下所有文件与文件夹
     *
     * @param parentId
     * @return
     */
    @Override
    public FolderVo listFileAndFolders(String parentId) {
        //根目录
        FolderVo folderVo = new FolderVo();
        List<FolderVo> folderVos = FindFordersInDatabases(parentId, "sss");

        List<FileVo> fileVos = fileService.getFileList(parentId);

        folderVo.setFileVos(fileVos);

        folderVo.setFolderVos(folderVos);

        recursivelyFindFileAndFolders(folderVos);
        return null;
    }

    private void recursivelyFindFileAndFolders(List<FolderVo> folderVos) {
        Queue<FolderVo> queue = new LinkedList<>();
        for (FolderVo folderVo : folderVos) {
            queue.offer(folderVo);
        }
        while (queue != null) {
            FolderVo poll = queue.poll();
            //搜寻该文件下所有文件夹和文件,将文件全部放入该poll的FileVos中
            if (fileService.getFileList(poll.folderId) != null) {
                poll.setFileVos(fileService.getFileList(poll.folderId));
            }

            //将文件全部放入该poll的FolderVos中,并将文件夹入队
            if (getFolders(poll.getFolderId()) != null) {
                poll.setFolderVos(getFolders(poll.getFolderId()));
            }
        }
    }
}

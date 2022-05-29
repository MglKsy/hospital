package com.thylovezj.hospital.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.common.Constant;
import com.thylovezj.hospital.dto.FileVo;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.mapper.FileMapper;
import com.thylovezj.hospital.pojo.File;
import com.thylovezj.hospital.service.FileService;
import com.thylovezj.hospital.service.OssService;
import com.thylovezj.hospital.util.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    @Resource
    private FileMapper fileMapper;

    private ExecutorService executorService;

    @Resource
    private OssService ossService;

    /**
     * public ThreadPoolExecutor(int corePoolSize,
     * int maximumPoolSize,
     * long keepAliveTime,
     * TimeUnit unit,
     * BlockingQueue<Runnable> workQueue)
     */

    public FileServiceImpl() {
        executorService = new ThreadPoolExecutor(3, 10, 20,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
    }

    /**
     *
     * @param folderId 根据文件夹Id获取该文件夹下所有文件Vo对象
     * @return
     */
    @Override
    public List<FileVo> getFileList(String folderId) {
        ArrayList<FileVo> fileVos = new ArrayList<>();
        //获取当前用户userId
        String uid = "sss";
        return FindFilesInDatabase(folderId, fileVos, uid);
    }

    /**
     *
     * @param folderId
     * @param fileVos
     * @param uid
     * @return
     */
    private ArrayList<FileVo> FindFilesInDatabase(String folderId, ArrayList<FileVo> fileVos, String uid) {
        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<File>()
                .eq("user_id", uid).eq("dir_id", folderId);

        List<File> files = fileMapper.selectList(fileQueryWrapper);
        files.stream().forEach((file) -> {
            FileVo fileVo = new FileVo();
            BeanUtils.copyProperties(file, fileVo);
            fileVos.add(fileVo);
        });
        return fileVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileVo addFile(MultipartFile file, String parentId) throws IOException {
        //获取文件名
        String fileFullName = file.getOriginalFilename();
        String fileName = fileFullName.substring(0, fileFullName.lastIndexOf("."));
        String fileType = fileFullName.substring(fileFullName.lastIndexOf(".") + 1);
        if (file.getSize() > Constant.MAX_UPLOAD_SIZE) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.UPLOAD_TOO_BIG);
        }
        String fileUri = ossService.upload(file, Constant.FILE_URI);
        executorService.submit(() -> {
            File result = new File();
            result.setDirId(parentId);
            result.setOldName(fileName);
            result.setFilePath(fileUri);
            result.setFileSize(Convert.toStr(file.getSize()));
            result.setFileType(fileType);
            result.setUpdateTime(new Date());
            result.setUserId("sss");
            fileMapper.insert(result);
        });
        FileVo fileVo = new FileVo();
        fileVo.setOldName(fileName);
        fileVo.setFileSize(Convert.toStr(file.getSize()));
        fileVo.setFilePath(fileUri);
        fileVo.setFileType(fileType);
        fileVo.setUpdateTime(new Date());
        fileVo.setDirId(parentId);
        return fileVo;
    }
}

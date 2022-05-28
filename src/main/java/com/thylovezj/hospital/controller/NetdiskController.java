package com.thylovezj.hospital.controller;


import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.FileVo;
import com.thylovezj.hospital.dto.FolderVo;
import com.thylovezj.hospital.pojo.Folder;
import com.thylovezj.hospital.request.FolderReq;
import com.thylovezj.hospital.service.FileService;
import com.thylovezj.hospital.service.FolderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
@Slf4j
@RequestMapping("/v1/netdisk")
public class NetdiskController {
    @Resource
    FileService fileService;

    @Resource
    FolderService folderService;
    /**
     *  返回该directoryId下全部文件以及文件夹
     *  如果是根路径，则传null
     *  @param directoryId
     * @return
     */
    @GetMapping("/list")
    public ApiRestResponse list(@RequestParam String directoryId){

        //跟据当前文件夹Id获取文件夹下所有文件
        List<FileVo> fileVos = fileService.getFileList(directoryId);
        List<FolderVo> folders = folderService.getFolders(directoryId);
        Map result = list2Map(fileVos, folders);
        return ApiRestResponse.success(result);
    }

    /**
     * 增加文件夹
     */
    @PostMapping("/add/folder")
    public ApiRestResponse addFolder(@RequestBody FolderReq folderReq){
        FolderVo folderVo = folderService.addFolder(folderReq);
        return ApiRestResponse.success(folderVo);
    }

    @PostMapping("/add/file")
    public ApiRestResponse addFile(@RequestBody MultipartFile file,@RequestParam String parentId) throws IOException {
        FileVo fileVo = fileService.addFile(file, parentId);
        return ApiRestResponse.success(fileVo);
    }

    @ApiOperation("获取该文件夹下所有文件")
    @PostMapping("/list/all")
    public ApiRestResponse listAll(){
        FolderVo folderVo = folderService.listFileAndFolders("");
        return ApiRestResponse.success(folderVo);
    }


    /**
     *将文件与文件夹进行封装
     * @param files 列表
     * @return
     */
    public static Map list2Map(List... files){
        HashMap<String, List> stringListHashMap = new HashMap<>();
        stringListHashMap.put("fileVos",files[0]);
        stringListHashMap.put("folders",files[1]);
        return stringListHashMap;
    }


}

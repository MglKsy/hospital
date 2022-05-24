package com.thylovezj.hospital.controller;


import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.FileVo;
import com.thylovezj.hospital.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/upload")
public class NetdiskController {
    @Resource
    FileService fileService;
    /**
     *  返回该fileId下全部文件以及文件夹
     *  如果是根路径，则传null
     *  @param directoryId
     * @return
     */
    @GetMapping("/list")
    public ApiRestResponse list(@RequestParam Long directoryId){
        //跟据当前文件夹Id获取文件夹下所有文件
        List<FileVo> fileList = fileService.getFileList(directoryId);

        return ApiRestResponse.success();
    }
}

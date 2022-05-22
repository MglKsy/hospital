package com.thylovezj.hospital.controller;


import com.thylovezj.hospital.common.ApiRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/v1/upload")
public class NetdiskController {
    @Resource

    /**
     *  返回该fileId下全部文件以及文件夹
     *  如果是根路径，则传null
     *  @param fileId
     * @return
     */
    @GetMapping("/list")
    public ApiRestResponse list(@RequestParam Long fileId){

        return ApiRestResponse.success();
    }
}

package com.thylovezj.hospital.controller;

import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.pojo.CgcAnswer;
import com.thylovezj.hospital.request.CgcAnswerReq;
import com.thylovezj.hospital.service.CgcAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 长谷川问题
 */
@RestController
@RequestMapping("/v1/answer")
public class CgcAnswerController {
    @Resource
    CgcAnswerService cgcAnswerService;

    @PostMapping("/upload")
    public ApiRestResponse uploadAnswer(@RequestBody List<CgcAnswerReq> cgcAnswerReqList){
        cgcAnswerService.uploadAns(cgcAnswerReqList);

        return ApiRestResponse.success();
    }
}

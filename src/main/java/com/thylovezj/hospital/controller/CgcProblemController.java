package com.thylovezj.hospital.controller;

import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.CgcProblemVo;
import com.thylovezj.hospital.request.CgcProblemReq;
import com.thylovezj.hospital.service.CgcProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/problem")
@Slf4j
public class CgcProblemController {
    @Autowired
    CgcProblemService cgcProblemService;


    /**
     * @return 返回长谷川问题表
     */
    @GetMapping("/getcgc")
    public ApiRestResponse getCgcProblem(){
        List<CgcProblemVo> problemList = cgcProblemService.getCgcProblem();
        return ApiRestResponse.success(problemList);
    }
}

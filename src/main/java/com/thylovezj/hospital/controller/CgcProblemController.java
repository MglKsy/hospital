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
     * @param subNum 客观题数量
     * @param objNum 主观题数量
     * @return
     */
    @GetMapping("/get")
    public ApiRestResponse getQuestion(@RequestParam("sub_number") int subNum, @RequestParam("obj_number") int objNum) {
        List<CgcProblemVo> problem = cgcProblemService.getProblem(subNum, objNum);
        return ApiRestResponse.success(problem);
    }

    /**
     * 这里对于图片问题的上传不支持
     * @param cgcProblemReq 添加问题请求对象
     * @return
     */
    @PostMapping("/add")
    public ApiRestResponse addProblem(@RequestBody CgcProblemReq cgcProblemReq) {
        cgcProblemService.addProblem(cgcProblemReq);
        return ApiRestResponse.success();
    }

    /**
     * @return 返回长谷川问题表
     */
    @GetMapping("/getcgc")
    public ApiRestResponse getCgcProblem(){
        List<CgcProblemVo> problemList = cgcProblemService.getCgcProblem();
        return ApiRestResponse.success(problemList);
    }
}

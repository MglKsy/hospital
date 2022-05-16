package com.thylovezj.hospital.controller;

import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.CgcProblemVo;
import com.thylovezj.hospital.dto.ProblemVo;
import com.thylovezj.hospital.request.CgcProblemReq;
import com.thylovezj.hospital.request.ProblemNumReq;
import com.thylovezj.hospital.request.ProblemReq;
import com.thylovezj.hospital.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/problem")
@Slf4j
public class ProblemController {
    @Autowired
    ProblemService problemService;

    /**
     *
     * @param problemNumReq 问题数对象
     * @return
     */
    @PostMapping("/get")
    public ApiRestResponse getQuestion(@RequestBody ProblemNumReq problemNumReq) {
        List<ProblemVo> problem = problemService.getProblem(problemNumReq.getSubNumber(), problemNumReq.getObjNumber(),problemNumReq.getPicNumber());
        return ApiRestResponse.success(problem);
    }

    /**
     * 这里对于图片问题的上传不支持
     *
     * @param problemReq 添加问题请求对象
     * @return
     */
    @PostMapping("/add")
    public ApiRestResponse addProblem(@RequestBody ProblemReq problemReq) {
        problemService.addProblem(problemReq);
        return ApiRestResponse.success();
    }
}

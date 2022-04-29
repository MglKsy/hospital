package com.thylovezj.hospital.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.common.Constant;
import com.thylovezj.hospital.dto.ProblemVo;
import com.thylovezj.hospital.pojo.Problem;
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
     * @param subNum 客观题数量
     * @param objNum 主观题数量
     * @return
     */
    @GetMapping("/get")
    public ApiRestResponse getQuestion(@RequestParam("sub_number") int subNum, @RequestParam("obj_number") int objNum) {
        List<ProblemVo> problem = problemService.getProblem(subNum, objNum);
        return ApiRestResponse.success(problem);
    }

    /**
     * 这里对于图片问题的上传不支持
     * @param problemReq 添加问题请求对象
     * @return
     */
    @PostMapping("/add")
    public ApiRestResponse addProblem(@RequestBody ProblemReq problemReq) {
        problemService.addProblem(problemReq);
        return ApiRestResponse.success();
    }

    /**
     *根据医生ID查询其添加问题，返回problemList对象，默认每页十条记录
     * @param doctor_id 医生Id
     * @param p 页号
     * @return
     */
    @GetMapping("/getselfproblem")
    public ApiRestResponse getSelfProblem(@RequestParam("doctor_id") Integer doctor_id,@RequestParam("p") Integer p){
        IPage<Problem> problemList = problemService.getProblemList(doctor_id,p, Constant.pageRows);
        return ApiRestResponse.success(problemList);
    }
}

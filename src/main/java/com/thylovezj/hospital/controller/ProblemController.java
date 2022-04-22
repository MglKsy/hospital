package com.thylovezj.hospital.controller;

import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.ProblemVo;
import com.thylovezj.hospital.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@Slf4j
public class ProblemController {
    @Autowired
    ProblemService problemService;

    /**
     *
     * @param subNum 客观题数量
     * @param objNum 主观题数量
     * @return
     */
    @GetMapping("/get")
    public ApiRestResponse getQuestion(@RequestParam("sub_number") int subNum, @RequestParam("obj_number") int objNum) {
        List<ProblemVo> problem = problemService.getProblem(subNum,objNum);
        return ApiRestResponse.success(problem);
    }
}

package com.thylovezj.hospital.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.ExerciseVo;
import com.thylovezj.hospital.pojo.Exercise;
import com.thylovezj.hospital.request.ExerciseReq;
import com.thylovezj.hospital.service.ExerciseService;
import com.thylovezj.hospital.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 训练记录Controller
 */

@RestController
@RequestMapping("/v1/exercise")
@Slf4j
public class ExerciseController {
    @Autowired
    ExerciseService exerciseService;

    @PostMapping("/add")
    public ApiRestResponse addExerciseRecord(@RequestBody ExerciseReq exerciseReq) {
        exerciseService.addExerciseRecord(exerciseReq);
        return ApiRestResponse.success();
    }

    @GetMapping("/get")
    public ApiRestResponse getExerciseRecord(@RequestParam int PageNum,@RequestParam int PageSize){
        List<Exercise> exerciseList = exerciseService.list(new QueryWrapper<Exercise>().eq("pid", "ss"));
        List<ExerciseVo> exerciseVos = exerciseList.stream().map(exercise -> {
            ExerciseVo exerciseVo = new ExerciseVo();
            BeanUtils.copyProperties(exercise, exerciseVo);
            return exerciseVo;
        }).collect(Collectors.toList());
        Page<ExerciseVo> page = new Page<>(PageNum, PageSize);
        Page<ExerciseVo> result = page.setRecords(exerciseVos);
        return ApiRestResponse.success(result);
    }
}

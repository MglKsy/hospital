package com.thylovezj.hospital.controller;

import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.pojo.Exercise;
import com.thylovezj.hospital.request.ExerciseReq;
import com.thylovezj.hospital.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 训练记录Controller
 */

@RestController
@RequestMapping("/v1/exercise")
public class ExerciseController {
    @Autowired
    ExerciseService exerciseService;

    @PostMapping("/add")
    public ApiRestResponse addExerciseRecord(@RequestBody ExerciseReq exerciseReq) {
        exerciseService.addExerciseRecord(exerciseReq);
        return ApiRestResponse.success();
    }
}

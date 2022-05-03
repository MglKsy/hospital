package com.thylovezj.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.thylovezj.hospital.pojo.Exercise;
import com.thylovezj.hospital.request.ExerciseReq;

public interface ExerciseService extends IService<Exercise> {

    void addExerciseRecord(ExerciseReq exerciseReq);
}

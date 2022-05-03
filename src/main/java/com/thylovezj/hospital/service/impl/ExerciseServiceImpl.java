package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.mapper.CgcProblemMapper;
import com.thylovezj.hospital.mapper.ExerciseMapper;
import com.thylovezj.hospital.pojo.CgcProblem;
import com.thylovezj.hospital.pojo.Exercise;
import com.thylovezj.hospital.request.ExerciseReq;
import com.thylovezj.hospital.service.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class ExerciseServiceImpl extends ServiceImpl<ExerciseMapper, Exercise> implements ExerciseService {
    @Resource
    ExerciseMapper exerciseMapper;

    @Override
    public void addExerciseRecord(ExerciseReq exerciseReq){
        Exercise exercise = new Exercise();
        BeanUtils.copyProperties(exerciseReq,exercise);
        exerciseMapper.insert(exercise);
    }
}

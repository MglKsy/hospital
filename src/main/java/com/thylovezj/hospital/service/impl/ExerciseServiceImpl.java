package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.mapper.CgcProblemMapper;
import com.thylovezj.hospital.mapper.ExerciseMapper;
import com.thylovezj.hospital.mapper.PatientMapper;
import com.thylovezj.hospital.pojo.CgcProblem;
import com.thylovezj.hospital.pojo.Exercise;
import com.thylovezj.hospital.pojo.Patient;
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
    @Resource
    PatientMapper patientMapper;
    @Override
    public void addExerciseRecord(ExerciseReq exerciseReq) {
        Exercise exercise = new Exercise();
        //通过病人open_id获得病人对象
        Patient patient = patientMapper.selectById(exerciseReq.getPid());
        //获得病人子女的open_id
        String sonId = patient.getSonId();
        exercise.setSonId(sonId);
        BeanUtils.copyProperties(exerciseReq, exercise);
        int insert = exerciseMapper.insert(exercise);
        if (insert == 0) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.INSERT_FAILED);
        }
    }
}

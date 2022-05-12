package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.mapper.CgcProblemMapper;
import com.thylovezj.hospital.mapper.ExerciseMapper;
import com.thylovezj.hospital.mapper.PatientMapper;
import com.thylovezj.hospital.mapper.UserMapper;
import com.thylovezj.hospital.pojo.CgcProblem;
import com.thylovezj.hospital.pojo.Exercise;
import com.thylovezj.hospital.pojo.Patient;
import com.thylovezj.hospital.request.ExerciseReq;
import com.thylovezj.hospital.service.ExerciseService;
import com.thylovezj.hospital.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class ExerciseServiceImpl extends ServiceImpl<ExerciseMapper, Exercise> implements ExerciseService {
    @Resource
    ExerciseMapper exerciseMapper;
    @Resource
    PatientMapper patientMapper;
    @Resource
    UserMapper userMapper;

    @Override
    public void addExerciseRecord(ExerciseReq exerciseReq) {
        Exercise exercise = new Exercise();
        //通过当前用户open_id
        String pid = UserHolder.getId();
        //获得当前病人对象
        Patient patient = patientMapper.selectById(pid);
        //获得病人子女的open_id
        String sonId = patient.getSonId();
        exercise.setSonId(sonId);
        BeanUtils.copyProperties(exerciseReq, exercise);
        exercise.setExDate(new Date());
        exercise.setPid(pid);
        int insert = exerciseMapper.insert(exercise);
        if (insert == 0) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.INSERT_FAILED);
        }
    }
}

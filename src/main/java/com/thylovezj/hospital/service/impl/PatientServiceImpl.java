package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.common.Constant;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.pojo.Patient;
import com.thylovezj.hospital.service.PatientService;
import com.thylovezj.hospital.mapper.PatientMapper;
import com.thylovezj.hospital.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author mlovek
 * @description 针对表【xdu_hospital_patient】的数据库操作Service实现
 * @createDate 2022-05-02 17:47:54
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient>
        implements PatientService {
    @Resource
    PatientMapper patientMapper;

    @Override
    public Integer addBonus() {
        //获取到当前用户的open_id
        String uid = UserHolder.getId();

        //根据open_id查询到病人对象
        Patient patient = patientMapper.selectById(uid);

        //查询更新积分日期
        Date updateTime = patient.getUpdateTime();

        //转换为毫秒数
        long update = updateTime.getTime();

        //查询现在日期
        Date date = new Date();

        //现在日期的毫秒数
        long now = date.getTime();

        if (now - update < 24 * 60 * 60 * 1000) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.BONUS_ADD_ERROR);
        }

        //获取过去的积分
        Integer bonusPresent = patient.getBonus();

        //增加积分
        patient.setBonus(bonusPresent+ Constant.addBonus);

        patientMapper.updateById(patient);

        //返回添加完的积分
        return patient.getBonus();
    }
}





package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.pojo.Patient;
import com.thylovezj.hospital.service.PatientService;
import com.thylovezj.hospital.mapper.PatientMapper;
import org.springframework.stereotype.Service;

/**
* @author mlovek
* @description 针对表【xdu_hospital_patient】的数据库操作Service实现
* @createDate 2022-05-02 17:47:54
*/
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient>
    implements PatientService{

}





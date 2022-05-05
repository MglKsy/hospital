package com.thylovezj.hospital.service;

import com.thylovezj.hospital.pojo.Patient;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author mlovek
* @description 针对表【xdu_hospital_patient】的数据库操作Service
* @createDate 2022-05-02 17:47:54
*/
public interface PatientService extends IService<Patient> {

    Integer addBonus();
}

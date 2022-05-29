package com.thylovezj.hospital.service;

import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.pojo.Patient;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author mlovek
* @description 针对表【xdu_hospital_patient】的数据库操作Service
* @createDate 2022-05-02 17:47:54
*/
public interface PatientService extends IService<Patient> {

    Integer addBonus();

    ApiRestResponse<String> sign();

    ApiRestResponse<Map<String,Integer>> signCount(Integer type);

    ApiRestResponse<Map<String,List<Integer>>> signRecord();

    ApiRestResponse<String> bind(String openId);

    ApiRestResponse<String> saveInfo(Patient patient);
}

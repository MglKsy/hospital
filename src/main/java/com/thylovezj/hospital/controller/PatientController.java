package com.thylovezj.hospital.controller;

import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.pojo.Patient;
import com.thylovezj.hospital.service.PatientService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ApiRestResponse<String> save(@RequestBody Patient patient) {
        boolean save = patientService.save(patient);
        if (save) {
            return ApiRestResponse.success("保存成功");
        } else {
            return ApiRestResponse.error(ThylovezjHospitalExceptionEnum.SAVE_ERROR);
        }
    }

    /**
     * 增加病人积分
     * @return 更新后的积分
     */
    @PostMapping("/add/bonus")
    public ApiRestResponse addBonus() {
        Integer bonus = patientService.addBonus();
        return ApiRestResponse.success(bonus);
    }

    /**
     * 获取签到天数
     * @return
     */
    @PostMapping("/sign")
    public ApiRestResponse<String> sign(){
        return patientService.sign();
    }

    /**
     * 获取连续签到天数
     * @return
     */
    @GetMapping("/sign/count")
    public ApiRestResponse<Map<String,Integer>> signCount(){
        return patientService.signCount();
    }

    @GetMapping("/sign/continuousCount")
    public ApiRestResponse<Map<String,Integer>> signContinuousCount(){
        return patientService.signContinuousCount();
    }

    @GetMapping("/sign/signRecord")
    public ApiRestResponse<Map<String,List<Integer>>> signRecord(){
        return patientService.signRecord();
    }
}

package com.thylovezj.hospital.controller;

import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.pojo.Patient;
import com.thylovezj.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ApiRestResponse<String> save(@RequestBody Patient patient){
        boolean save = patientService.save(patient);
        if (save){
            return ApiRestResponse.success("保存成功");
        }else {
            return ApiRestResponse.error(ThylovezjHospitalExceptionEnum.SAVE_ERROR);
        }
    }

}

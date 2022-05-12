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

    //TODO 这里需要确定前端提交的patient的参数，不能直接传patient对象
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
     * 获取签到天数
     * @param type type为0 查询这个月签到多少天， type为1 查询截止今天连续签到多少天（不一定是最长连续）
     * @return
     */
    @GetMapping("/sign/count/{type}")
    public ApiRestResponse<Map<String,Integer>> signCount(@PathVariable Integer type){
        return patientService.signCount(type);
    }



    @GetMapping("/sign/signRecord")
    public ApiRestResponse<Map<String,List<Integer>>> signRecord(){
        return patientService.signRecord();
    }
}

package com.thylovezj.hospital.controller;

import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.GestureVo;
import com.thylovezj.hospital.pojo.Gesture;
import com.thylovezj.hospital.service.GestureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/gesture")
public class GestureController {


    @Autowired
    private GestureService gestureService;

    @PostMapping
    public ApiRestResponse<GestureVo> getImage(String recognizedName){
        return gestureService.getImage(recognizedName);
    }
}

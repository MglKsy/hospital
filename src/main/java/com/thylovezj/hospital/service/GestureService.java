package com.thylovezj.hospital.service;

import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.GestureVo;
import com.thylovezj.hospital.pojo.Gesture;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author mlovek
* @description 针对表【xdu_hospital_gesture】的数据库操作Service
* @createDate 2022-05-20 12:43:34
*/
public interface GestureService extends IService<Gesture> {

    ApiRestResponse<GestureVo> getImage(String recognizedName);
}

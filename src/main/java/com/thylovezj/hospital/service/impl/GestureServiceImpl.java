package com.thylovezj.hospital.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.GestureVo;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.pojo.Gesture;
import com.thylovezj.hospital.service.GestureService;
import com.thylovezj.hospital.mapper.GestureMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author mlovek
* @description 针对表【xdu_hospital_gesture】的数据库操作Service实现
* @createDate 2022-05-20 12:43:34
*/
@Service
@Slf4j
public class GestureServiceImpl extends ServiceImpl<GestureMapper, Gesture>
    implements GestureService{

    @Autowired
    private GestureMapper gestureMapper;
    @Override
    public ApiRestResponse<GestureVo> getImage(String recognizedName) {
        LambdaQueryWrapper<Gesture> wrapper = new LambdaQueryWrapper<>();

        wrapper.like(Gesture::getRecognizeName,recognizedName);

        Gesture gesture = gestureMapper.selectOne(wrapper);
        if (gesture!=null){
            GestureVo gestureVo = BeanUtil.copyProperties(gesture, GestureVo.class);
            System.out.println(gestureVo);
            System.out.println();
            log.info("{}",gestureVo.toString());
            return ApiRestResponse.success(gestureVo);
        }
        throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.NO_CORRESPONDING_IMAGE);
    }
}





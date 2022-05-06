package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.common.Constant;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.pojo.Patient;
import com.thylovezj.hospital.service.PatientService;
import com.thylovezj.hospital.mapper.PatientMapper;
import com.thylovezj.hospital.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;

import static com.thylovezj.hospital.common.RedisKeyConstant.SIGN_USER_KEY;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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

    @Override
    public ApiRestResponse<String> sign() {
        //String key = getSignKsy();
        String key = "sign:mgl:202205";
        int dayOfMonth = LocalDateTime.now().getDayOfMonth();
        stringRedisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
        return ApiRestResponse.success("签到成功");
    }



    @Override
    public ApiRestResponse<Map<String,Integer>> signCount() {
        //1、获取key
        //String key = getSignKsy();
        String key = "sign:mgl:202205";

        //2、得到签到结果
        List<Long> result = getResult(key);


        Map<String,Integer> map = new HashMap<>();
        map.put("count",0);
        if (result == null || result.isEmpty()){
            return ApiRestResponse.success(map);
        }
        Long num = result.get(0);
        if (num==null || num == 0){
            return ApiRestResponse.success(map);
        }
        int count = 0;
        while (num != 0){
            //与运算，如果结果位0 说明没有签到，继续循环
            if ((num & 1) == 0) {
                num >>>= 1;
                continue;
            }else {
                count++;
            }
            //签到结果右移一位
            num >>>= 1;
        }
        map.put("count",count);
        return ApiRestResponse.success(map);
    }

    @Override
    public ApiRestResponse<Map<String,Integer>> signContinuousCount() {
        //1、获取key
        //String key = getSignKsy();
        String key = "sign:mgl:202205";

        //2、得到签到结果
        List<Long> result = getResult(key);

        Map<String,Integer> map = new HashMap<>();
        map.put("count",0);
        if (result == null || result.isEmpty()){
            return ApiRestResponse.success(map);
        }
        Long num = result.get(0);
        if (num==null || num == 0){
            return ApiRestResponse.success(map);
        }
        int count = 0;
        while (num != 0){
            //与运算，如果结果位0 说明没有签到，退出循环
            if ((num & 1) == 0) {
                break;
            }else {
                count++;
            }
            //签到结果右移一位
            num >>>= 1;
        }
        map.put("count",count);
        return ApiRestResponse.success(map);
    }

    @Override
    public ApiRestResponse<Map<String,List<Integer>>> signRecord() {
        //String key = getSignKsy();
        String key = "sign:mgl:202205";

        List<Long> result = getResult(key);
        if (result == null || result.isEmpty()){
            return null;
        }
        Long num = result.get(0);
        if (num==null || num == 0){
            return null;
        }
        int dayOfMonth = LocalDateTime.now().getDayOfMonth();
        List<Integer> list = new ArrayList<>();
        while (num != 0){
            if ((num&1) == 0){
                num >>>= 1;
                dayOfMonth--;
                continue;
            }else {
                list.add(dayOfMonth--);
            }
            num >>>= 1;
        }
        Collections.sort(list);
        Map<String,List<Integer>> map = new HashMap<>();
        map.put("days",list);
        return ApiRestResponse.success(map);
    }

    public String getSignKsy(){
        return SIGN_USER_KEY + UserHolder.getId() + LocalDateTime.now().format(DateTimeFormatter.ofPattern(":yyyyMM"));
    }
    public List<Long> getResult(String key){

        int dayOfMonth = LocalDateTime.now().getDayOfMonth();
        List<Long> result = stringRedisTemplate.opsForValue().bitField(
                key, BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)
        );

        return result;
    }

}





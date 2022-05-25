package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.common.Constant;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.pojo.Patient;
import com.thylovezj.hospital.service.PatientService;
import com.thylovezj.hospital.mapper.PatientMapper;
import com.thylovezj.hospital.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
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
        String key = getSignKsy();
//        String key = "sign:mgl:202205";
        int dayOfMonth = LocalDateTime.now().getDayOfMonth();
        stringRedisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
        return ApiRestResponse.success("签到成功");
    }



    @Override
    public ApiRestResponse<Map<String,Integer>> signCount(Integer type) {
        //1、获取key
        // 正式开发用getSignKsy()函数获取redis的键，下面只做模拟，
         String key = getSignKsy();
//        String key = "sign:mgl:202205";

        //2、得到签到结果
        List<Long> result = getResult(key);

        Map<String,Integer> map = new HashMap<>();
        //首先默认签到0天，如果下面是null直接返回结果
        map.put("count",0);
        if (result == null || result.isEmpty()){
            return ApiRestResponse.success(map);
        }
        Long num = result.get(0);
        if (num==null || num == 0){
            return ApiRestResponse.success(map);
        }
        //我们需要根据type来进行计算签到天数
        Integer count = null;

        //计算本月签到多少天
        if (type == 0){
            count = doIt(num, (item -> {
                int mount = 0;
                while (item != 0) {
                    //与运算，如果结果位0 说明没有签到，继续循环
                    if ((item & 1) == 0) {
                        item >>>= 1;
                        continue;
                    } else {
                        mount++;
                    }
                    //签到结果右移一位
                    item >>>= 1;
                }
                return mount;
            }));
        }
        //计算截止今天连续签到多少天
        else {
            count = doIt(num,(item)->{
                int mount = 0;
                while (item != 0){
                    if ((item & 1) == 0){
                        break;
                    }else {
                        mount ++;
                    }
                    item >>>= 1;
                }
                return mount;
            });
        }


        map.put("count",count);
        return ApiRestResponse.success(map);
    }


    @Override
    public ApiRestResponse<Map<String,List<Integer>>> signRecord() {
        // 正式开发用getSignKsy()函数获取redis的键，下面只做模拟，
        //String key = getSignKsy();

        String key = getSignKsy();

        List<Long> result = getResult(key);
        //如果为空,说明该用户无签到
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
            if ((num & 1) == 0){
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

    @Override
    public ApiRestResponse<String> bind(String openId) {
        String sonId = UserHolder.getId();
        Patient patient = new Patient();
        patient.setOpenId(openId);
        patient.setSonId(sonId);
        boolean flag = this.save(patient);
        if (flag){
            return ApiRestResponse.success();
        }else {
            return ApiRestResponse.error(ThylovezjHospitalExceptionEnum.BIND_ERROR);
        }
    }

    @Override
    public ApiRestResponse<String> saveInfo(Patient patient) {
        LambdaQueryWrapper<Patient> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Patient::getSonId,UserHolder.getId());
        boolean flag = this.update(patient, wrapper);
        if (flag){
            return ApiRestResponse.success("修改老人信息成功");
        }else {
            return ApiRestResponse.error(ThylovezjHospitalExceptionEnum.SAVE_PATIENT_INFO_ERROR);
        }
    }


    //获取前面的字符串sign:open_id:202205
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

    public Integer doIt(Long num,Function<Long,Integer> function){
        return function.apply(num);
    }
}





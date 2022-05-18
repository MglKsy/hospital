package com.thylovezj.hospital.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.thylovezj.hospital.common.Constant;
import com.thylovezj.hospital.common.RedisKeyConstant;
import com.thylovezj.hospital.dto.CgcProblemVo;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.mapper.CgcProblemMapper;
import com.thylovezj.hospital.pojo.CgcProblem;
import com.thylovezj.hospital.request.CgcProblemReq;
import com.thylovezj.hospital.service.CgcProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class CgcProblemServiceImpl extends ServiceImpl<CgcProblemMapper, CgcProblem> implements CgcProblemService {
    @Resource
    CgcProblemMapper cgcProblemMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;


    @Override
    public List<CgcProblemVo> getCgcProblem() throws JsonProcessingException {
        String JSONProblem = stringRedisTemplate.opsForValue().get(RedisKeyConstant.PROBLEM_CGC_KEY);
        if (StrUtil.isNotBlank(JSONProblem)){
            List<CgcProblemVo> cgcProblemVos = JSONObject.parseArray(JSONProblem, CgcProblemVo.class);
            return cgcProblemVos;
        }
        QueryWrapper queryWrapper = new QueryWrapper<>();
        List<CgcProblem> cgcProblemList = cgcProblemMapper.selectList(queryWrapper);
        List<CgcProblemVo> cgcProblemVoList = new ArrayList<>();

        cgcProblemList.stream().forEach((cgcProblem -> {
            CgcProblemVo cgcProblemVo = new CgcProblemVo();
            BeanUtils.copyProperties(cgcProblem, cgcProblemVo);
            cgcProblemVoList.add(cgcProblemVo);
        }));

//        for (CgcProblem cgcProblem:cgcProblemList) {
//            CgcProblemVo cgcProblemVo = new CgcProblemVo();
//            BeanUtils.copyProperties(cgcProblem, cgcProblemVo);
//            cgcProblemVoList.add(cgcProblemVo);
//        }
        //将List序列化为String进行存储
        String json = RedisKeyConstant.mapper.writeValueAsString(cgcProblemList);
        stringRedisTemplate.opsForValue().set(RedisKeyConstant.PROBLEM_CGC_KEY,json);
        return cgcProblemVoList;
    }
}

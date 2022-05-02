package com.thylovezj.hospital.service.impl;

import com.thylovezj.hospital.mapper.CgcAnswerMapper;
import com.thylovezj.hospital.pojo.CgcAnswer;
import com.thylovezj.hospital.request.CgcAnswerReq;
import com.thylovezj.hospital.service.CgcAnswerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class CgcAnswerServiceImpl implements CgcAnswerService {
    @Autowired
    CgcAnswerMapper cgcAnswerMapper;

    @Override
    public void uploadAns(List<CgcAnswerReq> cgcAnswerReqList) {
        //答题人的uuid
        String openId = cgcAnswerReqList.get(0).openId;

        //TODO 根据答题人的uuid,找到其对应子女


        //答题日期
        Date date = new Date();

        for (int i = 0; i < cgcAnswerReqList.size(); i++) {
            CgcAnswer cgcAnswer = new CgcAnswer();
            BeanUtils.copyProperties(cgcAnswerReqList.get(i),cgcAnswer);
            cgcAnswer.setCreateTime(date);

            //TODO 将查询到的关联子女存入属性
            cgcAnswer.setSonId();

            cgcAnswerMapper.insert(cgcAnswer);
        }

        //TODO 批改主观题答案

        //TODO 生成结果
    }
}

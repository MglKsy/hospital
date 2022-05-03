package com.thylovezj.hospital.service.impl;

import com.thylovezj.hospital.mapper.CgcAnswerMapper;
import com.thylovezj.hospital.mapper.CgcResultMapper;
import com.thylovezj.hospital.mapper.UserMapper;
import com.thylovezj.hospital.pojo.*;
import com.thylovezj.hospital.request.CgcAnswerReq;
import com.thylovezj.hospital.service.CgcAnswerService;
import com.thylovezj.hospital.service.CgcProblemService;
import com.thylovezj.hospital.service.PatientService;
import com.thylovezj.hospital.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CgcAnswerServiceImpl implements CgcAnswerService {
    @Resource
    CgcAnswerMapper cgcAnswerMapper;
    @Autowired
    PatientService patientService;
    @Autowired
    CgcProblemService cgcProblemService;
    @Resource
    CgcResultMapper cgcResultMapper;

    @Override
    public void uploadAns(List<CgcAnswerReq> cgcAnswerReqList) {


        //TODO 根据答题人的uuid,找到其对应子女
        //获取病人Id
        String pid = UserHolder.getId();
        Patient patient = patientService.getById(pid);

        //TODO 获取子女Id
        String sonId = patient.getSonId();

        //答题日期
        Date date = new Date();



        //TODO 批改主观题答案
        //客观题分数
        double subScore = 0;
        for (int i = 0;i<cgcAnswerReqList.size();i++){
            CgcProblem cgcProblem = cgcProblemService.getById(cgcAnswerReqList.get(i).getQuestionId());
            //如果长谷川问题为客观题，则批改
            if (cgcProblem.getType() == 1){
                if (cgcProblem.getAnswer() == cgcAnswerReqList.get(i).getSubAns()){
                    subScore += cgcProblem.getScore();
                }
            }
        }


        //TODO 生成结果
        //生成结果对象
        CgcResult cgcResult = new CgcResult();
        cgcResult.setPid(pid);
        cgcResult.setSonId(sonId);
        cgcResult.setCreateTime(date);
        cgcResult.setSubScore(subScore);
        cgcResult.setObjScore(0);
        cgcResult.setState(1);
        //获取到result表主键
        int resultId = cgcResultMapper.insert(cgcResult);

        for (int i = 0; i < cgcAnswerReqList.size(); i++) {
            CgcAnswer cgcAnswer = new CgcAnswer();
            BeanUtils.copyProperties(cgcAnswerReqList.get(i),cgcAnswer);
            cgcAnswer.setCreateTime(date);

            //TODO 将查询到的关联子女存入属性
            cgcAnswer.setSonId(sonId);
            cgcAnswer.setResultId(resultId);
            cgcAnswerMapper.insert(cgcAnswer);
        }
    }
}

package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.common.Constant;
import com.thylovezj.hospital.dto.ProblemVo;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.mapper.ProblemMapper;
import com.thylovezj.hospital.mapper.UserMapper;
import com.thylovezj.hospital.pojo.Problem;
import com.thylovezj.hospital.pojo.User;
import com.thylovezj.hospital.request.ProblemReq;
import com.thylovezj.hospital.service.ProblemService;
import com.thylovezj.hospital.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
    @Resource
    ProblemMapper problemMapper;

    @Override
    public List<ProblemVo> getProblem(int subNum, int objNum) {
        List<ProblemVo> problems = new ArrayList<>();
        //查看数据库中问题个数，sNum是客观题问题数量,oNum是主观题问题数量
        int sNum = problemMapper.calculateNum(Constant.subType);
        int oNum = problemMapper.calculateNum(Constant.objType);
        if (subNum > sNum || objNum > oNum) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.PROBLEM_NOT_ENOUGH);
        }
        //获取数据库中相应的问题
        List<Problem> subProblemList = problemMapper.getProblem(Constant.subType, subNum);
        List<Problem> objProblemList = problemMapper.getProblem(Constant.objType, objNum);
        for (Problem subProblem : subProblemList) {
            ProblemVo problemVo = new ProblemVo();
            BeanUtils.copyProperties(subProblem, problemVo);
            problems.add(problemVo);
        }
        for (Problem objProblem : objProblemList) {
            ProblemVo problemVo = new ProblemVo();
            BeanUtils.copyProperties(objProblem, problemVo);
            problems.add(problemVo);
        }
        return problems;
    }


    @Override
    public void addProblem(ProblemReq problemReq){
        Problem problem = new Problem();
        BeanUtils.copyProperties(problemReq,problem);
        problemMapper.insert(problem);
    }
}

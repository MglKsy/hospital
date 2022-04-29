package com.thylovezj.hospital.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
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


    /**
     * 添加问题方法
     * @param problemReq
     */
    @Override
    @Transactional
    public void addProblem(ProblemReq problemReq){
        Problem problem = new Problem();
        BeanUtils.copyProperties(problemReq,problem);
        //问题创建时间
        problem.setCreateTime(new DateTime());
        //问题更新时间
        problem.setUpdateTime(new DateTime());
        int count = problemMapper.insert(problem);
        if (count == 0){
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.INSERT_FAILED);
        }
    }


    /**
     *
     * @param doctor_id 医生ID
     * @param page 页数
     * @param rows 每页记录数
     * @return
     */
    @Override
    public IPage<Problem> getProblemList(Integer doctor_id, Integer page, Integer rows){
        Page<Problem> p = new Page<Problem>(page,rows);
        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        problemQueryWrapper.eq("doctor_id",doctor_id);
        problemQueryWrapper.orderByDesc("update_time");
        IPage<Problem> problemPage = problemMapper.selectPage(p, problemQueryWrapper);
        return problemPage;
    }
}

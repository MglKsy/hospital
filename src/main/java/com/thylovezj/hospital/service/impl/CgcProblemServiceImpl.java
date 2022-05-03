package com.thylovezj.hospital.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.common.Constant;
import com.thylovezj.hospital.dto.CgcProblemVo;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.mapper.CgcProblemMapper;
import com.thylovezj.hospital.pojo.CgcProblem;
import com.thylovezj.hospital.request.CgcProblemReq;
import com.thylovezj.hospital.service.CgcProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class CgcProblemServiceImpl extends ServiceImpl<CgcProblemMapper, CgcProblem> implements CgcProblemService {
    @Resource
    CgcProblemMapper cgcProblemMapper;

    @Override
    public List<CgcProblemVo> getProblem(int subNum, int objNum) {
        List<CgcProblemVo> problems = new ArrayList<>();
        //查看数据库中问题个数，sNum是客观题问题数量,oNum是主观题问题数量
        int sNum = cgcProblemMapper.calculateNum(Constant.subType);
        int oNum = cgcProblemMapper.calculateNum(Constant.objType);
        if (subNum > sNum || objNum > oNum) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.PROBLEM_NOT_ENOUGH);
        }
        //获取数据库中相应的问题
        List<CgcProblem> subCgcProblemList = cgcProblemMapper.getProblem(Constant.subType, subNum);
        List<CgcProblem> objCgcProblemList = cgcProblemMapper.getProblem(Constant.objType, objNum);
        for (CgcProblem subCgcProblem : subCgcProblemList) {
            CgcProblemVo cgcProblemVo = new CgcProblemVo();
            BeanUtils.copyProperties(subCgcProblem, cgcProblemVo);
            problems.add(cgcProblemVo);
        }
        for (CgcProblem objCgcProblem : objCgcProblemList) {
            CgcProblemVo cgcProblemVo = new CgcProblemVo();
            BeanUtils.copyProperties(objCgcProblem, cgcProblemVo);
            problems.add(cgcProblemVo);
        }
        return problems;
    }


    /**
     * 添加问题方法
     *
     * @param cgcProblemReq
     */
    @Override
    @Transactional
    public void addProblem(CgcProblemReq cgcProblemReq) {
        CgcProblem cgcProblem = new CgcProblem();
        BeanUtils.copyProperties(cgcProblemReq, cgcProblem);
        //问题创建时间
        cgcProblem.setCreateTime(new DateTime());
        //问题更新时间
        cgcProblem.setUpdateTime(new DateTime());
        int count = cgcProblemMapper.insert(cgcProblem);
        if (count == 0) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.INSERT_FAILED);
        }
    }


    /**
     * @param doctor_id 医生ID
     * @param page      页数
     * @param rows      每页记录数
     * @return
     */
    @Override
    public IPage<CgcProblem> getProblemList(Integer doctor_id, Integer page, Integer rows) {
        Page<CgcProblem> p = new Page<CgcProblem>(page, rows);
        QueryWrapper<CgcProblem> problemQueryWrapper = new QueryWrapper<>();
        problemQueryWrapper.eq("doctor_id", doctor_id);
        problemQueryWrapper.orderByDesc("update_time");
        IPage<CgcProblem> problemPage = cgcProblemMapper.selectPage(p, problemQueryWrapper);
        return problemPage;
    }

    @Override
    public List<CgcProblemVo> getCgcProblem() {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        List<CgcProblem> cgcProblemList = cgcProblemMapper.selectList(queryWrapper);
        List<CgcProblemVo> cgcProblemVoList = new ArrayList<>();
        for (int i = 0; i < cgcProblemList.size(); i++) {
            CgcProblemVo cgcProblemVo = new CgcProblemVo();
            BeanUtils.copyProperties(cgcProblemList.get(i), cgcProblemVo);
            cgcProblemVoList.add(cgcProblemVo);
        }
        return cgcProblemVoList;
    }
}

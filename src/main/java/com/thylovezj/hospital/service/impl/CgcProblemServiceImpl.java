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

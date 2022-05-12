package com.thylovezj.hospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.thylovezj.hospital.dto.CgcProblemVo;
import com.thylovezj.hospital.pojo.CgcProblem;
import com.thylovezj.hospital.request.CgcProblemReq;

import java.util.List;

public interface CgcProblemService extends IService<CgcProblem> {


    List<CgcProblemVo> getCgcProblem() throws JsonProcessingException;
}

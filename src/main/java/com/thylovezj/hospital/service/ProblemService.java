package com.thylovezj.hospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thylovezj.hospital.dto.ProblemVo;
import com.thylovezj.hospital.pojo.Problem;
import com.thylovezj.hospital.pojo.User;
import com.thylovezj.hospital.request.ProblemReq;

import java.util.List;

public interface ProblemService extends IService<Problem> {
    List<ProblemVo> getProblem(int subNum, int objNum);

    void addProblem(ProblemReq problemReq);

    IPage<Problem> getProblemList(Integer doctor_id, Integer page, Integer rows);
}

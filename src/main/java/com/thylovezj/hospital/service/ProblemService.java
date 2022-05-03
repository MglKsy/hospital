package com.thylovezj.hospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thylovezj.hospital.dto.CgcProblemVo;
import com.thylovezj.hospital.pojo.CgcProblem;
import com.thylovezj.hospital.pojo.Problem;
import com.thylovezj.hospital.request.CgcProblemReq;
import com.thylovezj.hospital.request.ProblemReq;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProblemService extends IService<Problem> {
    List<CgcProblemVo> getProblem(int subNum, int objNum);

    @Transactional
    void addProblem(ProblemReq problemReq);

    IPage<Problem> getProblemList(Integer doctor_id, Integer page, Integer rows);
}

package com.thylovezj.hospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thylovezj.hospital.dto.CgcProblemVo;
import com.thylovezj.hospital.pojo.CgcProblem;
import com.thylovezj.hospital.request.CgcProblemReq;

import java.util.List;

public interface CgcProblemService extends IService<CgcProblem> {
    List<CgcProblemVo> getProblem(int subNum, int objNum);

    void addProblem(CgcProblemReq cgcProblemReq);

    IPage<CgcProblem> getProblemList(Integer doctor_id, Integer page, Integer rows);

    List<CgcProblemVo> getCgcProblem();
}

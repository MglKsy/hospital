package com.thylovezj.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thylovezj.hospital.dto.ProblemVo;
import com.thylovezj.hospital.pojo.Problem;
import com.thylovezj.hospital.pojo.User;

import java.util.List;

public interface ProblemService extends IService<Problem> {
    List<ProblemVo> getProblem(int subNum, int objNum);
}

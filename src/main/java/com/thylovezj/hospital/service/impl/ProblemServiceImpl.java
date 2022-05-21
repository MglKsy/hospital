package com.thylovezj.hospital.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.thylovezj.hospital.common.Constant;
import com.thylovezj.hospital.common.RedisKeyConstant;
import com.thylovezj.hospital.dto.ProblemVo;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.mapper.ProblemMapper;

import com.thylovezj.hospital.pojo.Problem;

import com.thylovezj.hospital.request.ProblemReq;
import com.thylovezj.hospital.service.ProblemService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
    @Resource
    ProblemMapper problemMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public List<ProblemVo> getProblem(int subNum, int objNum, int picNum) {
        List<ProblemVo> problems = new ArrayList<>();
        //去redis里查找是否有主观题，客观题和图片题的数量
        String sNum = stringRedisTemplate.opsForValue().get(RedisKeyConstant.PROBLEM_SUB_NUM);
        String oNum = stringRedisTemplate.opsForValue().get(RedisKeyConstant.PROBLEM_OBJ_NUM);
        String pNum = stringRedisTemplate.opsForValue().get(RedisKeyConstant.PROBLEM_PIC_NUM);


        doIt(subNum, sNum, RedisKeyConstant.PROBLEM_SUB_NUM, Constant.subType, problems);

        //如果redis里存在客观题个数
        doIt(objNum, oNum, RedisKeyConstant.PROBLEM_OBJ_NUM, Constant.objType, problems);

        doIt(picNum, pNum, RedisKeyConstant.PROBLEM_PIC_NUM, Constant.picType, problems);

        return problems;
    }

    /**
     * @param num      请求的问题个数
     * @param Num      redis里存储的题库个数
     * @param redisKey 数据库里问题的key
     * @param type     数据库里存储的问题类型
     */
    private void doIt(int num, String Num, String redisKey, Integer type, List<ProblemVo> problemVos) {

        //如果redis里存在主观题个数
        if (Num != null) {
            int inum = Integer.parseInt(Num);
            //判断redis里存在的主观题个数是否小于所要求获取的主观题个数
            if (inum >= num) {
                //大于请求主观题个数,从数据库查询出主观题
                List<Problem> problemList = getProblems(num, redisKey);
                problemList.stream().forEach(problem -> {
                    ProblemVo problemVo = new ProblemVo();
                    BeanUtils.copyProperties(problem, problemVo);
                    problemVos.add(problemVo);
                });
                return;
            }

        }
        //小于请求主观题个数
        //去数据库查询
        int inum = problemMapper.calculateNum(type);
        //判断是否仍小于数据库题数
        if (inum >= num) {
            List<Problem> problemList = getProblems(num, redisKey);
            problemList.stream().forEach(problem -> {
                ProblemVo problemVo = new ProblemVo();
                BeanUtils.copyProperties(problem, problemVo);
                problemVos.add(problemVo);
            });
            String s = String.valueOf(problemList.size());
            //默认一天过期
            stringRedisTemplate.opsForValue().set(redisKey, s, RedisKeyConstant.PROBLEM_EXPIRE_TIME, TimeUnit.MINUTES);
        } else {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.PROBLEM_NOT_ENOUGH);
        }
    }

    /**
     * 从数据库里获取问题列表
     *
     * @param Num      需要获取问题的个数
     * @param redisKey 需要获取问题的类型
     * @return 问题列表
     */
    private List<Problem> getProblems(int Num, String redisKey) {
        int ptype;
        switch (redisKey) {
            case RedisKeyConstant.PROBLEM_SUB_NUM:
                ptype = Constant.subType;
                break;
            case RedisKeyConstant.PROBLEM_OBJ_NUM:
                ptype = Constant.objType;
                break;
            case RedisKeyConstant.PROBLEM_PIC_NUM:
                ptype = Constant.picType;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + redisKey);
        }
        List<Problem> problemList = problemMapper.getProblem(ptype, Num);
        return problemList;
    }


    /**
     * 添加问题方法
     *
     * @param problemReq
     */
    @Override
    @Transactional
    public void addProblem(ProblemReq problemReq) {
        Problem problem = new Problem();
        BeanUtils.copyProperties(problemReq, problem);
        //问题创建时间
        problem.setCreateTime(new DateTime());
        //问题更新时间
        problem.setUpdateTime(new DateTime());
        int count = problemMapper.insert(problem);
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
    public IPage<Problem> getProblemList(Integer doctor_id, Integer page, Integer rows) {
        Page<Problem> p = new Page<Problem>(page, rows);
        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<Problem>()
                .eq("doctor_id", doctor_id).orderByDesc("update_time");
//        problemQueryWrapper.eq("doctor_id", doctor_id);
//        problemQueryWrapper.orderByDesc("update_time");
        IPage<Problem> problemPage = problemMapper.selectPage(p, problemQueryWrapper);
        return problemPage;
    }

}

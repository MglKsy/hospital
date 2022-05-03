package com.thylovezj.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thylovezj.hospital.pojo.CgcProblem;
import com.thylovezj.hospital.pojo.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProblemMapper extends BaseMapper<Problem> {
    @Select("select * from xdu_hospital_problem where type = #{type} order by rand() limit #{num}")
    List<CgcProblem> getProblem(@Param("type") Integer type, @Param("num") Integer num);

    @Select("select count(*) from xdu_hospital_problem where type = #{type}")
    int calculateNum(@Param("type") Integer type);
}

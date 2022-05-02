package com.thylovezj.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thylovezj.hospital.pojo.CgcProblem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author THYLOVEZJ
 * @description 针对表【xdu_hospital_problem(微信用户信息)】的数据库操作Mapper
 * @createDate 2022-04-21 16:31:19
 * @Entity com.thylovezj.hospital.pojo.User
 */
@Mapper
public interface CgcProblemMapper extends BaseMapper<CgcProblem> {

    @Select("select * from xdu_hospital_problem where type = #{type} order by rand() limit #{num}")
    List<CgcProblem> getProblem(@Param("type") Integer type, @Param("num") Integer num);

    @Select("select count(*) from xdu_hospital_problem where type = #{type}")
    int calculateNum(@Param("type") Integer type);

}

package com.thylovezj.hospital.mapper;

import com.thylovezj.hospital.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
* @author mlovek
* @description 针对表【xdu_hospital_user(微信用户信息)】的数据库操作Mapper
* @createDate 2022-04-21 16:31:19
* @Entity com.thylovezj.hospital.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("update xdu_hospital_user set last_visit_time = CURRENT_TIMESTAMP where open_id = #{openid}")
    void updateUpdateTime(@Param("openid")String openid);
}





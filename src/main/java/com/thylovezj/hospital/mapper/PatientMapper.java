package com.thylovezj.hospital.mapper;

import com.thylovezj.hospital.pojo.Patient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author mlovek
* @description 针对表【xdu_hospital_patient】的数据库操作Mapper
* @createDate 2022-05-02 17:47:54
* @Entity com.thylovezj.hospital.pojo.Patient
*/
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {

}





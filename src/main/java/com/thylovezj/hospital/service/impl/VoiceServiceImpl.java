package com.thylovezj.hospital.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.mapper.PatientMapper;
import com.thylovezj.hospital.pojo.Patient;
import com.thylovezj.hospital.pojo.Voice;
import com.thylovezj.hospital.service.VoiceService;
import com.thylovezj.hospital.mapper.VoiceMapper;
import com.thylovezj.hospital.util.UserHolder;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author mlovek
* @description 针对表【xdu_hospital_voice】的数据库操作Service实现
* @createDate 2022-05-28 17:20:03
*/
@Service
public class VoiceServiceImpl extends ServiceImpl<VoiceMapper, Voice>
    implements VoiceService{

    @Autowired
    private VoiceMapper voiceMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public ApiRestResponse<String> saveVoice(Voice voice) {

        String sonId = UserHolder.getId();

        LambdaQueryWrapper<Patient> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Patient::getSonId,sonId);
        Patient patient = patientMapper.selectOne(wrapper);
        String patientId = patient.getOpenId();

        voice.setOpenId(patientId);
        int insertCount = voiceMapper.insert(voice);
        if (insertCount == 0){
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.SAVE_VOICE_ERROR);
        }
        return ApiRestResponse.success("添加声音成功");
    }

    @Override
    public ApiRestResponse<Voice> getOneVoice() {
        String patientId = UserHolder.getId();

        LambdaQueryWrapper<Voice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Voice::getOpenId,patientId);

        Long aLong = voiceMapper.selectCount(wrapper);
        int index = RandomUtil.randomInt(0, Integer.parseInt(aLong.toString()));

        List<Voice> voices = voiceMapper.selectList(wrapper);

        if (voices == null || voices.size() == 0){
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.NOT_HAVE_VOICE);
        }
        return ApiRestResponse.success(voices.get(index));
    }
}





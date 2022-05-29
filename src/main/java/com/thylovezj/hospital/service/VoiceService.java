package com.thylovezj.hospital.service;

import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.pojo.Voice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author mlovek
* @description 针对表【xdu_hospital_voice】的数据库操作Service
* @createDate 2022-05-28 17:20:03
*/
public interface VoiceService extends IService<Voice> {

    ApiRestResponse<String> saveVoice(Voice voice);

    ApiRestResponse<Voice> getOneVoice();
}

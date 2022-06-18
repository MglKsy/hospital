package com.thylovezj.hospital.controller;


import cn.hutool.db.Page;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.pojo.Voice;
import com.thylovezj.hospital.service.VoiceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/voice")
public class VoiceController {

    @Autowired
    private VoiceService voiceService;

    @PostMapping
    public ApiRestResponse<String> saveVoice(@RequestBody Voice voice){
        return voiceService.saveVoice(voice);
    }

    @GetMapping
    public ApiRestResponse<Voice> getOneVoice(){
        return voiceService.getOneVoice();
    }
}

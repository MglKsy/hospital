package com.thylovezj.hospital.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.SongVo;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.pojo.Song;
import com.thylovezj.hospital.request.SongReq;
import com.thylovezj.hospital.service.SongService;
import com.thylovezj.hospital.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/v1/song")
@Slf4j
public class SongController {
    @Resource
    public SongService songService;

    /**
     * 添加喜欢的音乐
     */
    @PostMapping("/add")
    public ApiRestResponse addSong(@RequestBody SongReq songReq) {
        songService.addSong(songReq);
        return ApiRestResponse.success();
    }

    /**
     * 移除喜欢音乐的接口（一次移除一首歌曲）
     */
    @PostMapping("/remove")
    public ApiRestResponse removeSong(@RequestParam Integer songId) {
        SongVo songVo = songService.removeSong(songId);
        return ApiRestResponse.success(songVo);
    }

    /**
     * 显示添加歌曲列表
     */
    @GetMapping("/get")
    public ApiRestResponse getSongList() {
        List<SongVo> songList = songService.getSongList();
        return ApiRestResponse.success(songList);
    }
}

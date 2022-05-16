package com.thylovezj.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.thylovezj.hospital.dto.SongVo;
import com.thylovezj.hospital.pojo.Song;
import com.thylovezj.hospital.request.SongReq;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SongService extends IService<Song> {

    @Transactional
    void addSong(SongReq songReq);

    SongVo removeSong(Integer songId);

    List<SongVo> getSongList();
}

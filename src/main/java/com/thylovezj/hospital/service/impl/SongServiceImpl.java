package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.thylovezj.hospital.common.RedisKeyConstant;
import com.thylovezj.hospital.dto.SongVo;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.mapper.ProblemMapper;
import com.thylovezj.hospital.mapper.SongMapper;
import com.thylovezj.hospital.pojo.Problem;
import com.thylovezj.hospital.pojo.Song;
import com.thylovezj.hospital.pojo.User;
import com.thylovezj.hospital.request.SongReq;
import com.thylovezj.hospital.service.ProblemService;
import com.thylovezj.hospital.service.SongService;
import com.thylovezj.hospital.util.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {
    @Resource
    public SongMapper songMapper;
    @Resource
    public StringRedisTemplate stringRedisTemplate;

    @Override
    public void addSong(SongReq songReq) {
        Integer songId = songReq.getSongId();
        String openId = UserHolder.getId();
        LambdaQueryWrapper<Song> songLambdaQueryWrapper = new LambdaQueryWrapper<>();
        songLambdaQueryWrapper.eq(Song::getSongId, songId);
        songLambdaQueryWrapper.eq(Song::getOpenId, openId);
        long count = this.count(songLambdaQueryWrapper);
        //如果当前用户添加过该歌曲，那么抛出异常
        if (count != 0) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.REPEAT_ADD_SONG);
        }
        //如果未存在，那么添加音乐
        Song song = new Song();
        song.setOpenId(openId);
        BeanUtils.copyProperties(songReq, song);
        //否则添加音乐
        int insert = songMapper.insert(song);
        if (insert == 0) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.UPLOAD_SONG_FAILED);
        }
    }

    @Override
    public SongVo removeSong(Integer songId) {
        String openId = UserHolder.getId();
        LambdaQueryWrapper<Song> songLambdaQueryWrapper = new LambdaQueryWrapper<>();
        songLambdaQueryWrapper.eq(Song::getSongId, songId);
        songLambdaQueryWrapper.eq(Song::getOpenId, openId);
        long count = this.count(songLambdaQueryWrapper);
        //如果count == 0说明歌曲已经被删除
        if (count == 0) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.ALREADY_DELETE_SONG);
        }
        //删除歌曲
        SongVo songVo = new SongVo();
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.eq("song_id",songId);
        Song song = songMapper.selectOne(songQueryWrapper);
        BeanUtils.copyProperties(song, songVo);
        songMapper.delete(songLambdaQueryWrapper);
        return songVo;
    }

    @Override
    public List<SongVo> getSongList(){
        String openId = UserHolder.getId();
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.eq("open_id", openId);
        List<Song> songs = songMapper.selectList(songQueryWrapper);
        List<SongVo> list = new ArrayList<>();
        for (Song song : songs) {
            SongVo songVo = new SongVo();
            BeanUtils.copyProperties(song, songVo);
            list.add(songVo);
        }
        return list;
    }
}

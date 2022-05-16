package com.thylovezj.hospital.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value ="xdu_hospital_song")
@Data
public class SongReq implements Serializable {
    /**
     * 歌曲Id
     */
    private Integer songId;

    /**
     * 歌曲名称
     */
    private String songName;

    /**
     * 歌曲专辑名
     */
    private String album;

    /**
     * 歌曲资源文件路径
     */
    private String src;

    /**
     * 歌手名称
     */
    private String singer;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

package com.thylovezj.hospital.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName(value = "xdu_hospital_song")
@Data
public class SongVo implements Serializable {
    public String songName;

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

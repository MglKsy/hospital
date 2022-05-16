package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@TableName(value ="xdu_hospital_song")
@Data
public class Song implements Serializable {
    /**
     * 歌曲表主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户主键
     */
    private String openId;

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

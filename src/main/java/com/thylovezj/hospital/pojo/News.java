package com.thylovezj.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("xdu_hospital_news")
@Data
public class News implements Serializable {
    /**
     * 新闻表主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 新闻表banner图uri
     */
    private String pic;

    /**
     * 新闻表主要内容
     */
    private String content;

    /**
     * 新闻表标题
     */
    private String title;

    /**
     * 新闻表状态
     */
    private Integer state;

    /**
     * 新闻表更新日期
     */
    private Date updateTime;

    /**
     * 新闻表添加人的openId
     */
    private String openId;

    /**
     * 新闻点赞数
     */
    private Integer likes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

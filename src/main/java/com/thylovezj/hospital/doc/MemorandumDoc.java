package com.thylovezj.hospital.doc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thylovezj.hospital.pojo.Memorandum;
import lombok.Data;

import java.util.Date;

/**
 * @author thylovezj
 */
@TableName(value = "xdu_hospital_memorandum")
@Data
public class MemorandumDoc {
    private Integer id;

    private String openId;

    private String title;

    private Date updateTime;

    private String content;

    public MemorandumDoc(){

    }

    public MemorandumDoc(Memorandum memorandum){
        this.openId = memorandum.getOpenId();
        this.content = memorandum.getContent();
        this.title = memorandum.getTitle();
        this.updateTime = memorandum.getUpdateTime();
    }

    public MemorandumDoc(Memorandum memorandum,int id){
        this.id = id;
        this.openId = memorandum.getOpenId();
        this.content = memorandum.getContent();
        this.title = memorandum.getTitle();
        this.updateTime = memorandum.getUpdateTime();
    }
}

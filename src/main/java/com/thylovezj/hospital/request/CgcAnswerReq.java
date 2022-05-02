package com.thylovezj.hospital.request;

import lombok.Data;

@Data
public class CgcAnswerReq {
    public String openId;

    public Integer questionId;

    public String ans;
}

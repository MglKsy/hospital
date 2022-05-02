package com.thylovezj.hospital.service;

import com.thylovezj.hospital.request.CgcAnswerReq;

import java.util.List;

public interface CgcAnswerService {

    void uploadAns(List<CgcAnswerReq> cgcAnswerReqList);
}

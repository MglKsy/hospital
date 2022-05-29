package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.mapper.MemorandumMapper;
import com.thylovezj.hospital.pojo.Memorandum;
import com.thylovezj.hospital.service.MemorandumService;
import org.springframework.stereotype.Service;

/**
 * @author thylovezj
 */

@Service
public class MemorandumServiceImpl extends ServiceImpl<MemorandumMapper, Memorandum> implements MemorandumService {
}

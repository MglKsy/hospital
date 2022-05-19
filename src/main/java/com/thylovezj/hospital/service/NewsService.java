package com.thylovezj.hospital.service;


import com.baomidou.mybatisplus.core.metadata.IPage;

public interface NewsService {
    IPage getNewsList(Integer pageNum, Integer pageSize);
}

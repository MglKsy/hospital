package com.thylovezj.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thylovezj.hospital.mapper.NewsMapper;
import com.thylovezj.hospital.pojo.News;
import com.thylovezj.hospital.service.NewsService;
import com.thylovezj.hospital.util.UserHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NewsServiceImpl implements NewsService {
    @Resource
    private NewsMapper newsMapper;

    @Override
    public IPage getNewsList(Integer pageNum, Integer pageSize) {
        Page<News> newsPage = new Page<News>(pageNum,pageSize);
        QueryWrapper<News> queryWrapper = new QueryWrapper<News>().eq("state", 1).orderByDesc("update_time");
        Page<News> result = newsMapper.selectPage(newsPage, queryWrapper);
        return result;
    }
}

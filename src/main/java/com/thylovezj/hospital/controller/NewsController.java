package com.thylovezj.hospital.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/v1/news")
public class NewsController {
    @Resource
    private NewsService newsService;

    /**
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/get")
    public ApiRestResponse getNewsList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        IPage newsList = newsService.getNewsList(pageNum, pageSize);
        return ApiRestResponse.success(newsList);
    }
}

package com.thylovezj.hospital.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.MemorandumDetailVo;
import com.thylovezj.hospital.dto.MemorandumSimpleVo;
import com.thylovezj.hospital.pojo.Memorandum;
import com.thylovezj.hospital.request.MemorandumReq;
import com.thylovezj.hospital.service.MemorandumService;
import com.thylovezj.hospital.util.UserHolder;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/v1/memorandum")
public class MemorandumController {
    @Resource
    MemorandumService memorandumService;

    @PostMapping("/add")
    public ApiRestResponse addMemor(@RequestBody MemorandumReq memorandumReq) {
        Memorandum memorandum = new Memorandum();
        BeanUtils.copyProperties(memorandumReq, memorandum);
        memorandum.setUpdateTime(new Date());
        memorandum.setOpenId(UserHolder.getId());
        boolean save = memorandumService.save(memorandum);
        return ApiRestResponse.success(save);
    }


    @PostMapping("/delete/{recordId}")
    public ApiRestResponse deleteMemor(@PathVariable("recordId") int recordId) {
        boolean remove = memorandumService.remove(new QueryWrapper<Memorandum>()
                .eq("open_id", UserHolder.getId()).eq("id", recordId));
        return ApiRestResponse.success(remove);
    }

    @GetMapping("/get/simple")
    public ApiRestResponse getSimpleMemor() {
        List<MemorandumSimpleVo> memorandumSimpleVos = memorandumService
                .list(new QueryWrapper<Memorandum>().eq("open_id", UserHolder.getId()))
                .stream().map((memorandum) -> {
                    MemorandumSimpleVo memorandumSimpleVo = new MemorandumSimpleVo();
                    BeanUtils.copyProperties(memorandum, memorandumSimpleVo);
                    return memorandumSimpleVo;
                }).collect(Collectors.toList());
        return ApiRestResponse.success(memorandumSimpleVos);
    }

    @GetMapping("/get/detail/{recordId}")
    public ApiRestResponse getDetailMemor(@PathVariable("recordId") int recordId) {
        List<MemorandumDetailVo> memorandumDetailVos = memorandumService.list(new QueryWrapper<Memorandum>()
                .eq("open_id", UserHolder.getId()).eq("id", recordId))
                .stream().map((memorandum -> {
                    MemorandumDetailVo memorandumDetailVo = new MemorandumDetailVo();
                    memorandumDetailVo.setContent(memorandum.getContent());
                    return memorandumDetailVo;
                })).collect(Collectors.toList());
        return ApiRestResponse.success(memorandumDetailVos);
    }
}

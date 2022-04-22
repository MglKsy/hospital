package com.thylovezj.hospital.controller;

import com.thylovezj.hospital.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Slf4j
public class ProblemController {
    @Autowired
    ProblemService problemService;


}

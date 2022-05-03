package com.thylovezj.hospital.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OssService {

    String uploadAvatar(MultipartFile file) throws IOException;
}

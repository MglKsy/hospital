package com.thylovezj.hospital.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OssService {
    /**
     *
     * @param file
     * @param uploadUri
     * @return
     * @throws IOException
     */
    String upload(MultipartFile file,String uploadUri) throws IOException;
}

package com.jlumart.service;

import org.springframework.web.multipart.MultipartFile;

public interface ComonService {
    String upload(MultipartFile file);
}

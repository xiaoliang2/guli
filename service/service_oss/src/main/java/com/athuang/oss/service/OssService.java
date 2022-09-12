package com.athuang.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-09-12 17:13
 **/
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}

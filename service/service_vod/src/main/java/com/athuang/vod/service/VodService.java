package com.athuang.vod.service;/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-09-20 18:54
 **/

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-20 18:54
 **/

public interface VodService {
    //上传视频到阿里云
    String uploadVideoAly(MultipartFile file);

    void removeMoreAlyVideo(List videoIdList);

//    void removeAlyVideo(String videoId);
}


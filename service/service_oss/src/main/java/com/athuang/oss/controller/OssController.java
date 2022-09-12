package com.athuang.oss.controller;


import com.athuang.commonutils.R;
import com.athuang.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-12 17:14
 **/
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

//    上传头像方法
    @PostMapping
    public R uploadOssFile(MultipartFile file){

//        获取上传文件 multipartfile
        String url = ossService.uploadFileAvatar(file);

        return R.ok().data("url",url);
    }
}

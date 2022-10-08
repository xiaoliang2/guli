package com.athuang.vod.service.impl;/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-09-20 18:56
 **/

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.athuang.commonutils.R;
import com.athuang.servicebase.exceptionhandler.GuliException;
import com.athuang.vod.service.VodService;
import com.athuang.vod.utils.ConstantVodUtils;
import com.athuang.vod.utils.InitVodClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-20 18:56
 **/

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideoAly(MultipartFile file) {

        try {
            // accessKeyId, accessKeySecret
            // fileName：上传文件原始名称
            //  01.03.09.mp4
            String fileName = file.getOriginalFilename();
            // title：上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            // inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

//             System.out.println(ConstantVodUtils.ACCESS_KEY_ID);
//             System.out.println(ConstantVodUtils.ACCESS_KEY_SECRET);
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { // 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void removeMoreAlyVideo(List videoIdList) {

        //        初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
//        创建删除时评request对象
        DeleteVideoRequest request = new DeleteVideoRequest();
//         向request设置视频id
//         request.setVideoIds(videoId);
        String videoIds = StringUtils.join(videoIdList.toArray(),",");
//         向request设置视频id
//         System.out.println(videoIds);
        request.setVideoIds(videoIds);
        try {
            client.getAcsResponse(request);

        } catch (ClientException e) {
            e.printStackTrace();

            throw new GuliException(20001,"删除视频失败");
        }
//         vodService.removeAlyVideo(videoId);
    }

//     @Override
//     public void removeAlyVideo(String videoId) {
//
//         QueryWrapper<>
//     }
}

package com.athuang.vod.controller;/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-09-19 17:57
 **/


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.athuang.commonutils.R;
import com.athuang.servicebase.exceptionhandler.GuliException;
import com.athuang.vod.service.VodService;
import com.athuang.vod.utils.ConstantVodUtils;
import com.athuang.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-19 17:57
 **/
@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;
//     上传视频到阿里云
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file)
    {
//         返回上传视频id
        String videoId = vodService.uploadVideoAly(file);

        return R.ok().data("videoId",videoId);
    }

    @DeleteMapping("removeAlyVideo/{videoId}")
    public R removeAlyVideo(@PathVariable String videoId)
    {
//        初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
//        创建删除时评request对象
        DeleteVideoRequest request = new DeleteVideoRequest();
//         向request设置视频id
        request.setVideoIds(videoId);
        try {
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();

            throw new GuliException(20001,"删除视频失败");
        }
//         vodService.removeAlyVideo(videoId);

    }

//     删除多个aliyun视频
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList)
    {
        vodService.removeMoreAlyVideo(videoIdList);

        return R.ok();
    }
//     根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id)
    {
        try {
//             创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);

//             创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
//             向request设置视频id
            request.setVideoId(id);
//             调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);


        }catch (Exception e)
        {
            throw new GuliException(20001,"获取凭证失败");
        }
    }


}

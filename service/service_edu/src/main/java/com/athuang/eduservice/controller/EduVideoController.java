package com.athuang.eduservice.controller;


import com.athuang.commonutils.R;
import com.athuang.eduservice.client.VodClient;
import com.athuang.eduservice.entity.EduVideo;
import com.athuang.eduservice.service.EduVideoService;
import com.athuang.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-15
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {


    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

//     添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo)
    {
        videoService.save(eduVideo);

        return R.ok();
    }

//     删除小节并且删除阿里云中的视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String  id)
    {

//         根据小节id获取视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
//         判断小节是否有视频id
//         根据视频id，实现远程调用实现视频删除
        if(!StringUtils.isEmpty(videoSourceId))
        {
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode() == 20001)
            {
                throw new GuliException(20001,"服务熔断了");
            }
        }

//       删除小节
        videoService.removeById(id);

        return R.ok();
    }

//     修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo)
    {
        videoService.updateById(eduVideo);

        return R.ok();
    }

//     根据id查询小节
    @PostMapping("getVideo/{videoId}")
    public R getVideo(@PathVariable String videoId)
    {
        EduVideo eduVideo =  videoService.getById(videoId);

        return R.ok().data("eduVideo",eduVideo);

    }

}


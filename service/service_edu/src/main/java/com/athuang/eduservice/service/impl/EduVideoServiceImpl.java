package com.athuang.eduservice.service.impl;

import com.athuang.eduservice.client.VodClient;
import com.athuang.eduservice.entity.EduVideo;
import com.athuang.eduservice.mapper.EduVideoMapper;
import com.athuang.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-09-15
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;
    @Override
    public void removeVideoByCourseId(String courseId) {

//         根据课程id查询视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);

//         List<Eduvideo> 变成 List<String>
        List<String> videoIds = new ArrayList<>();

        for (int i = 0;i < eduVideoList.size(); i++)
        {
            EduVideo eduVideo = eduVideoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId))
            {
                //             放到videoIds集合里面
                videoIds.add(videoSourceId);
//                 System.out.println(videoIds.get(i));
            }


        }
        if (videoIds.size()>0)
        {
            //         根据多个视频id删除多个视频
            vodClient.deleteBatch(videoIds);
        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}

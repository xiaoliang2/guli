package com.athuang.eduservice.service.impl;

import com.athuang.eduservice.entity.EduChapter;
import com.athuang.eduservice.entity.EduVideo;
import com.athuang.eduservice.entity.chapter.ChapterVo;
import com.athuang.eduservice.entity.chapter.VideoVo;
import com.athuang.eduservice.mapper.EduChapterMapper;
import com.athuang.eduservice.service.EduChapterService;
import com.athuang.eduservice.service.EduVideoService;
import com.athuang.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-09-15
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

//    注入小节service
    @Autowired
    private EduVideoService videoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

//        根据课程id查询所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();

        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

//        根据课程id查询所有小节

        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();

        wrapperVideo.eq("course_id",courseId);

        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

//        创建list用于最终封装
        List<ChapterVo> finalList = new ArrayList<>();

//        遍历章节封装
        for (int i = 0; i < eduChapterList.size(); i++)
        {
//            每个章节
            EduChapter eduChapter = eduChapterList.get(i);
//            eduChapter对象值复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
//            把chapterVo放到最终list集合
            finalList.add(chapterVo);
//        遍历小节封装

//            创建集合对小节进行封装
            List<VideoVo> videoList = new ArrayList<>();
            for (int m = 0 ;m < eduVideoList.size();m++)
            {
//                得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
//                判断，小节里面chapter和章节id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId()))
                {
//                    进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
//                    放到小节封装集合
                    videoList.add(videoVo);
                }
//
            }
//            把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoList);

        }




        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
//        根据chapterid查询小节表，有小节不删除，没有则删除

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);

//        判断，如果count大于1，则说明有小节
        if (count > 0)
        {
            throw new GuliException(20001,"不能删除");
        }else{
            int result = baseMapper.deleteById(chapterId);

            return result > 0;
        }

    }

    @Override
    public void removeChapterByCourseId(String courseId) {

        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}

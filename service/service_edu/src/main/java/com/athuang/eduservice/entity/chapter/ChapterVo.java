package com.athuang.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: guli_parent
 *
 * @description: 章节实体类
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-17 15:50
 **/
@Data
public class ChapterVo {

    private String id;

    private String title;

//    表示小节

    private List<VideoVo> children = new ArrayList<>();
}

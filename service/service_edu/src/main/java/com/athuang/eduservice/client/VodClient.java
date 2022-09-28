package com.athuang.eduservice.client;

import com.athuang.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-09-21 18:43
 **/
@FeignClient(value = "service-vod",fallback = VodClientImpl.class)
@Component
public interface VodClient {

//    定义调用的方法路径
//    根据视频id删除阿里云视频
@DeleteMapping("/eduvod/video/removeAlyVideo/{videoId}")
public R removeAlyVideo(@PathVariable("videoId") String videoId);

//    定义删除多个视频的方法
@DeleteMapping("/eduvod/video/delete-batch")
public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
